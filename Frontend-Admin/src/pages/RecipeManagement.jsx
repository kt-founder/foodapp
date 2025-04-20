import { useState, useEffect } from 'react';
import { FaTrash, FaEdit, FaEye, FaPlus, FaTimes } from 'react-icons/fa';
import { motion, AnimatePresence } from 'framer-motion';
import { getRecipes, addRecipe, deleteRecipe, updateRecipe } from '../api/recipe';
import { getCategories } from '../api/typefood';

const NUTRITION_FIELDS = ['Chất đường', 'Chất béo', 'Chất đạm', 'Chất xơ', 'calo'];

const defaultNutrition = {
  'Chất đường': 0,
  'Chất béo': 0,
  'Chất đạm': 0,
  'Chất xơ': 0,
  'calo': 0
};

const parseNutrition = (nutritionData) => {
  if (!nutritionData) return defaultNutrition;
  
  try {
    // Nếu là chuỗi số cách nhau bởi dấu phẩy
    if (typeof nutritionData === 'string' && nutritionData.includes(',')) {
      const values = nutritionData.split(',').map(val => parseFloat(val.trim()) || 0);
      return NUTRITION_FIELDS.reduce((obj, field, index) => {
        obj[field] = values[index] || 0;
        return obj;
      }, {});
    }
    
    // Nếu đã là object
    if (typeof nutritionData === 'object') {
      return nutritionData;
    }

    // Thử parse JSON
    const parsed = JSON.parse(nutritionData);
    return parsed || defaultNutrition;
  } catch (err) {
    console.error('Lỗi parse nutrition:', err);
    return defaultNutrition;
  }
};

const formatNutritionForApi = (nutritionObj) => {
  return Object.values(nutritionObj).join(',');
};

const RecipeManagement = () => {
  const [recipes, setRecipes] = useState([]);
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [showAddForm, setShowAddForm] = useState(false);
  const [showEditForm, setShowEditForm] = useState(false);
  const [showViewModal, setShowViewModal] = useState(false);
  const [selectedRecipe, setSelectedRecipe] = useState(null);
  const [editingRecipe, setEditingRecipe] = useState(null);
  const [newRecipe, setNewRecipe] = useState({
    name: '',
    detail: '',
    time: '',
    video: '',
    guide: '',
    ingredient: '',
    nutrition: formatNutritionForApi(defaultNutrition),
    image: [],
    typefoods: []
  });

  useEffect(() => {
    fetchRecipes();
    fetchCategories();
  }, []);

  const fetchRecipes = async () => {
    try {
      const data = await getRecipes();
      // Đảm bảo nutrition data hợp lệ cho mỗi recipe
      const processedData = data.map(recipe => ({
        ...recipe,
        nutrition: typeof recipe.nutrition === 'string' 
          ? recipe.nutrition 
          : JSON.stringify(parseNutrition(recipe.nutrition))
      }));
      setRecipes(processedData);
      setLoading(false);
    } catch (err) {
      setError(`Không thể tải danh sách công thức: ${err.message}`);
      setLoading(false);
    }
  };

  const fetchCategories = async () => {
    try {
      const data = await getCategories();
      setCategories(data);
    } catch (err) {
      setError(`Không thể tải danh sách loại món: ${err.message}`);
    }
  };

  const handleAddRecipe = async (e) => {
    e.preventDefault();
    try {
      const nutritionObj = parseNutrition(newRecipe.nutrition);
      const adminId = parseInt(localStorage.getItem('id'));
      
      if (!adminId) {
        throw new Error('Không tìm thấy thông tin admin, vui lòng đăng nhập lại');
      }

      const recipeToAdd = {
        ...newRecipe,
        nutrition: formatNutritionForApi(nutritionObj),
        idAut: adminId,
        image: newRecipe.image || [],
        typefoods: newRecipe.typefoods || []
      };

      await addRecipe(recipeToAdd);
      setShowAddForm(false);
      setNewRecipe({
        name: '',
        detail: '',
        time: '',
        video: '',
        guide: '',
        ingredient: '',
        nutrition: formatNutritionForApi(defaultNutrition),
        image: [],
        typefoods: []
      });
      fetchRecipes();
    } catch (err) {
      setError(`Không thể thêm công thức mới: ${err.message}`);
    }
  };

  const handleDeleteRecipe = async (recipeId) => {
    try {
      await deleteRecipe(recipeId);
      fetchRecipes();
    } catch (err) {
      setError(`Không thể xóa những công thức đã được yêu thích`);
    }
  };

  const handleEditClick = (recipe) => {
    setEditingRecipe({
      ...recipe,
      nutrition: recipe.nutrition || formatNutritionForApi(defaultNutrition)
    });
    setShowEditForm(true);
  };

  const handleUpdateRecipe = async (e) => {
    e.preventDefault();
    try {
      const nutritionObj = parseNutrition(editingRecipe.nutrition);
      const adminId = parseInt(localStorage.getItem('id'));
      
      if (!adminId) {
        throw new Error('Không tìm thấy thông tin admin, vui lòng đăng nhập lại');
      }

      // Giữ nguyên imageBase64 nếu không thay đổi ảnh
      const requestBody = {
        id: editingRecipe.id,
        name: editingRecipe.name,
        detail: editingRecipe.detail,
        time: editingRecipe.time,
        image: editingRecipe.image || [],
        imageBase64: editingRecipe.imageBase64, // Giữ nguyên ảnh cũ
        video: editingRecipe.video,
        guide: editingRecipe.guide,
        ingredient: editingRecipe.ingredient,
        nutrition: formatNutritionForApi(nutritionObj),
        idAut: adminId,
        typefoods: editingRecipe.typefoods?.map(type => ({
          id: type.id,
          name: type.name,
          img: type.img || []
        })) || []
      };

      await updateRecipe(editingRecipe.id, requestBody);
      setShowEditForm(false);
      setEditingRecipe(null);
      fetchRecipes();
    } catch (err) {
      setError(`Không thể cập nhật công thức: ${err.message}`);
    }
  };

  const convertToBase64 = (file) => {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        const base64String = reader.result.split(',')[1];
        resolve(base64String);
      };
      reader.onerror = (error) => reject(error);
    });
  };

  const handleImageUpload = async (e) => {
    const files = Array.from(e.target.files);
    try {
      const base64Promises = files.map(file => convertToBase64(file));
      const base64Results = await Promise.all(base64Promises);
      setNewRecipe({
        ...newRecipe,
        image: base64Results
      });
    } catch (err) {
      setError('Không thể tải ảnh lên: ' + err.message);
    }
  };

  const handleCategorySelect = (categoryId) => {
    const selectedCategory = categories.find(cat => cat.id === categoryId);
    if (selectedCategory) {
      setNewRecipe(prev => ({
        ...prev,
        typefoods: [...prev.typefoods, {
          id: selectedCategory.id,
          name: selectedCategory.name,
          img: selectedCategory.img || []
        }]
      }));
    }
  };

  const handleRemoveCategory = (categoryId) => {
    setNewRecipe(prev => ({
      ...prev,
      typefoods: prev.typefoods.filter(type => type.id !== categoryId)
    }));
  };

  const handleRemoveImage = (index) => {
    setNewRecipe(prev => ({
      ...prev,
      image: prev.image.filter((_, i) => i !== index)
    }));
  };

  const handleEditImageUpload = async (e) => {
    const files = Array.from(e.target.files);
    try {
      const base64Promises = files.map(file => convertToBase64(file));
      const base64Results = await Promise.all(base64Promises);
      setEditingRecipe({
        ...editingRecipe,
        image: base64Results
      });
    } catch (err) {
      setError('Không thể tải ảnh lên: ' + err.message);
    }
  };

  const handleRemoveEditImage = (index) => {
    setEditingRecipe(prev => ({
      ...prev,
      image: prev.image.filter((_, i) => i !== index)
    }));
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center h-screen">
        <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-orange-500"></div>
      </div>
    );
  }

  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      className="p-6"
    >
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">Quản lý công thức</h1>
        <button
          onClick={() => setShowAddForm(true)}
          className="bg-orange-500 text-white px-4 py-2 rounded-lg hover:bg-orange-600 flex items-center"
        >
          <FaPlus className="mr-2" />
          Thêm công thức
        </button>
      </div>
      
      {error && (
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
          {error}
        </div>
      )}

      {showAddForm && (
        <motion.div
          initial={{ opacity: 0, y: -20 }}
          animate={{ opacity: 1, y: 0 }}
          className="bg-white rounded-lg shadow p-6 mb-6"
        >
          <div className="flex justify-between items-center mb-4">
            <h2 className="text-lg font-semibold">Thêm công thức mới</h2>
            <button
              onClick={() => setShowAddForm(false)}
              className="text-gray-500 hover:text-gray-700"
            >
              <FaTimes />
            </button>
          </div>
          <form onSubmit={handleAddRecipe} className="space-y-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Tên món
              </label>
              <input
                type="text"
                value={newRecipe.name}
                onChange={(e) => setNewRecipe({ ...newRecipe, name: e.target.value })}
                className="w-full border rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-orange-500"
                required
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Chi tiết
              </label>
              <textarea
                value={newRecipe.detail}
                onChange={(e) => setNewRecipe({ ...newRecipe, detail: e.target.value })}
                className="w-full border rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-orange-500"
                rows="3"
                required
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Thời gian nấu
              </label>
              <input
                type="text"
                value={newRecipe.time}
                onChange={(e) => setNewRecipe({ ...newRecipe, time: e.target.value })}
                className="w-full border rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-orange-500"
                required
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Link video
              </label>
              <input
                type="url"
                value={newRecipe.video}
                onChange={(e) => setNewRecipe({ ...newRecipe, video: e.target.value })}
                className="w-full border rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-orange-500"
                required
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Hướng dẫn nấu
              </label>
              <textarea
                value={newRecipe.guide}
                onChange={(e) => setNewRecipe({ ...newRecipe, guide: e.target.value })}
                className="w-full border rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-orange-500"
                rows="5"
                required
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Nguyên liệu
              </label>
              <textarea
                value={newRecipe.ingredient}
                onChange={(e) => setNewRecipe({ ...newRecipe, ingredient: e.target.value })}
                className="w-full border rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-orange-500"
                rows="3"
                required
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Thông tin dinh dưỡng
              </label>
              <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                {NUTRITION_FIELDS.map((nutrient) => (
                  <div key={nutrient} className="flex flex-col">
                    <label className="text-sm text-gray-600 mb-1">{nutrient}</label>
                    <input
                      type="number"
                      min="0"
                      step="0.1"
                      value={parseNutrition(newRecipe.nutrition)[nutrient] || 0}
                      onChange={(e) => {
                        const nutritionObj = parseNutrition(newRecipe.nutrition);
                        const updatedNutrition = {
                          ...nutritionObj,
                          [nutrient]: parseFloat(e.target.value) || 0
                        };
                        setNewRecipe({
                          ...newRecipe,
                          nutrition: formatNutritionForApi(updatedNutrition)
                        });
                      }}
                      className="w-full border rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-orange-500"
                    />
                    <span className="text-xs text-gray-500 mt-1">{nutrient === 'calo' ? 'kcal' : 'g'}</span>
                  </div>
                ))}
              </div>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Hình ảnh
              </label>
              <div className="space-y-2">
                <input
                  type="file"
                  accept="image/*"
                  multiple
                  onChange={handleImageUpload}
                  className="block w-full text-sm text-gray-500
                    file:mr-4 file:py-2 file:px-4
                    file:rounded-full file:border-0
                    file:text-sm file:font-semibold
                    file:bg-orange-50 file:text-orange-700
                    hover:file:bg-orange-100"
                />
                <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-4 mt-2">
                  {newRecipe.image?.map((img, index) => (
                    <div key={index} className="relative group">
                      <img
                        src={`data:image/jpeg;base64,${img}`}
                        alt={`Preview ${index + 1}`}
                        className="w-full h-32 object-cover rounded-lg"
                      />
                      <button
                        type="button"
                        onClick={() => handleRemoveImage(index)}
                        className="absolute top-1 right-1 bg-red-500 text-white p-1 rounded-full 
                          opacity-0 group-hover:opacity-100 transition-opacity"
                      >
                        <FaTimes size={12} />
                      </button>
                    </div>
                  ))}
                </div>
              </div>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Loại món ăn
              </label>
              <div className="space-y-2">
                <select
                  onChange={(e) => handleCategorySelect(parseInt(e.target.value))}
                  className="w-full border rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-orange-500"
                >
                  <option value="">Chọn loại món</option>
                  {categories.map((category) => (
                    <option key={category.id} value={category.id}>
                      {category.name}
                    </option>
                  ))}
                </select>
                <div className="flex flex-wrap gap-2 mt-2">
                  {newRecipe.typefoods.map((type) => (
                    <span
                      key={type.id}
                      className="inline-flex items-center px-3 py-1 rounded-full text-sm font-medium bg-orange-100 text-orange-800"
                    >
                      {type.name}
                      <button
                        type="button"
                        onClick={() => handleRemoveCategory(type.id)}
                        className="ml-2 text-orange-600 hover:text-orange-800"
                      >
                        <FaTimes size={12} />
                      </button>
                    </span>
                  ))}
                </div>
              </div>
            </div>

            <div className="flex justify-end space-x-4">
              <button
                type="button"
                onClick={() => setShowAddForm(false)}
                className="px-4 py-2 border rounded-lg hover:bg-gray-100"
              >
                Hủy
              </button>
              <button
                type="submit"
                className="bg-orange-500 text-white px-4 py-2 rounded-lg hover:bg-orange-600"
              >
                Thêm
              </button>
            </div>
          </form>
        </motion.div>
      )}

      <div className="bg-white rounded-lg shadow overflow-hidden">
        <table className="min-w-full">
          <thead className="bg-gray-50">
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Tên món
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Thời gian
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Loại món
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Thao tác
              </th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {recipes.map((recipe) => (
              <motion.tr
                key={recipe.id}
                initial={{ opacity: 0, y: 20 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration: 0.3 }}
              >
                <td className="px-6 py-4 whitespace-nowrap">
                  <div className="flex items-center">
                    {recipe.imageBase64 && (
                      <img
                        src={`data:image/jpeg;base64,${recipe.imageBase64}`}
                        alt={recipe.name}
                        className="h-12 w-12 rounded-lg object-cover mr-3"
                      />
                    )}
                    <div>
                      <div className="text-sm font-medium text-gray-900">{recipe.name}</div>
                      <div className="text-sm text-gray-500">{recipe.detail}</div>
                    </div>
                  </div>
                </td>
                <td className="px-6 py-4 whitespace-nowrap">
                  <div className="text-sm text-gray-900">{recipe.time}</div>
                </td>
                <td className="px-6 py-4 whitespace-nowrap">
                  <div className="flex flex-wrap gap-2">
                    {recipe.typefoods.map((type) => (
                      <span
                        key={type.id}
                        className="px-2 py-1 text-xs font-semibold rounded-full bg-blue-100 text-blue-800"
                      >
                        {type.name}
                      </span>
                    ))}
                  </div>
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                  <button
                    onClick={() => {
                      setSelectedRecipe(recipe);
                      setShowViewModal(true);
                    }}
                    className="text-blue-600 hover:text-blue-900 mr-4"
                  >
                    <FaEye />
                  </button>
                  <button 
                    onClick={() => handleEditClick(recipe)}
                    className="text-yellow-600 hover:text-yellow-900 mr-4"
                  >
                    <FaEdit />
                  </button>
                  <button
                    onClick={() => handleDeleteRecipe(recipe.id)}
                    className="text-red-600 hover:text-red-900"
                  >
                    <FaTrash />
                  </button>
                </td>
              </motion.tr>
            ))}
          </tbody>
        </table>
      </div>

      <AnimatePresence>
        {showViewModal && selectedRecipe && (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
          >
            <motion.div
              initial={{ scale: 0.8, opacity: 0 }}
              animate={{ scale: 1, opacity: 1 }}
              exit={{ scale: 0.8, opacity: 0 }}
              className="bg-white rounded-lg shadow-lg p-6 w-full max-w-4xl max-h-[90vh] overflow-y-auto"
            >
              <div className="flex justify-between items-center mb-4">
                <h2 className="text-2xl font-bold">{selectedRecipe.name}</h2>
                <button
                  onClick={() => {
                    setShowViewModal(false);
                    setSelectedRecipe(null);
                  }}
                  className="text-gray-500 hover:text-gray-700"
                >
                  <FaTimes />
                </button>
              </div>

              <div className="space-y-4">
                <div>
                  <h3 className="text-lg font-semibold mb-2">Hình ảnh</h3>
                  {selectedRecipe.imageBase64 ? (
                    <img
                      src={`data:image/jpeg;base64,${selectedRecipe.imageBase64}`}
                      alt={selectedRecipe.name}
                      className="w-full max-w-md rounded-lg shadow-lg"
                    />
                  ) : (
                    <p className="text-gray-500">Không có hình ảnh</p>
                  )}
                </div>

                <div>
                  <h3 className="text-lg font-semibold mb-2">Chi tiết</h3>
                  <p className="text-gray-600">{selectedRecipe.detail}</p>
                </div>

                <div>
                  <h3 className="text-lg font-semibold mb-2">Thời gian nấu</h3>
                  <p className="text-gray-600">{selectedRecipe.time}</p>
                </div>

                <div>
                  <h3 className="text-lg font-semibold mb-2">Video hướng dẫn</h3>
                  <a
                    href={selectedRecipe.video}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="text-blue-600 hover:text-blue-800"
                  >
                    Xem video
                  </a>
                </div>

                <div>
                  <h3 className="text-lg font-semibold mb-2">Hướng dẫn nấu</h3>
                  <p className="text-gray-600 whitespace-pre-line">{selectedRecipe.guide}</p>
                </div>

                <div>
                  <h3 className="text-lg font-semibold mb-2">Nguyên liệu</h3>
                  <p className="text-gray-600">{selectedRecipe.ingredient}</p>
                </div>

                <div>
                  <h3 className="text-lg font-semibold mb-2">Thông tin dinh dưỡng</h3>
                  <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
                    {selectedRecipe && Object.entries(parseNutrition(selectedRecipe.nutrition)).map(([key, value]) => (
                      <div 
                        key={key}
                        className="flex items-center justify-between bg-orange-100 p-3 rounded-lg"
                      >
                        <span className="font-medium text-orange-800">{key}</span>
                        <div className="flex items-center">
                          <span className="text-orange-900 font-bold">{value}</span>
                          <span className="ml-1 text-orange-700 text-sm">{key === 'calo' ? 'kcal' : 'g'}</span>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>

                <div>
                  <h3 className="text-lg font-semibold mb-2">Loại món</h3>
                  <div className="flex flex-wrap gap-2">
                    {selectedRecipe.typefoods.map((type) => (
                      <span
                        key={type.id}
                        className="px-3 py-1 text-sm font-semibold rounded-full bg-blue-100 text-blue-800"
                      >
                        {type.name}
                      </span>
                    ))}
                  </div>
                </div>
              </div>
            </motion.div>
          </motion.div>
        )}
      </AnimatePresence>

      {showEditForm && editingRecipe && (
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          exit={{ opacity: 0 }}
          className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
        >
          <motion.div
            initial={{ scale: 0.8, opacity: 0 }}
            animate={{ scale: 1, opacity: 1 }}
            exit={{ scale: 0.8, opacity: 0 }}
            className="bg-white rounded-lg shadow-lg p-6 w-full max-w-4xl max-h-[90vh] overflow-y-auto"
          >
            <div className="flex justify-between items-center mb-4">
              <h2 className="text-lg font-semibold">Sửa công thức</h2>
              <button
                onClick={() => {
                  setShowEditForm(false);
                  setEditingRecipe(null);
                }}
                className="text-gray-500 hover:text-gray-700"
              >
                <FaTimes />
              </button>
            </div>

            <form onSubmit={handleUpdateRecipe} className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Tên món
                </label>
                <input
                  type="text"
                  value={editingRecipe.name}
                  onChange={(e) => setEditingRecipe({ ...editingRecipe, name: e.target.value })}
                  className="w-full border rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-orange-500"
                  required
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Chi tiết
                </label>
                <textarea
                  value={editingRecipe.detail}
                  onChange={(e) => setEditingRecipe({ ...editingRecipe, detail: e.target.value })}
                  className="w-full border rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-orange-500"
                  rows="3"
                  required
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Thời gian nấu
                </label>
                <input
                  type="text"
                  value={editingRecipe.time}
                  onChange={(e) => setEditingRecipe({ ...editingRecipe, time: e.target.value })}
                  className="w-full border rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-orange-500"
                  required
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Link video
                </label>
                <input
                  type="url"
                  value={editingRecipe.video}
                  onChange={(e) => setEditingRecipe({ ...editingRecipe, video: e.target.value })}
                  className="w-full border rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-orange-500"
                  required
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Hướng dẫn nấu
                </label>
                <textarea
                  value={editingRecipe.guide}
                  onChange={(e) => setEditingRecipe({ ...editingRecipe, guide: e.target.value })}
                  className="w-full border rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-orange-500"
                  rows="5"
                  required
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Nguyên liệu
                </label>
                <textarea
                  value={editingRecipe.ingredient}
                  onChange={(e) => setEditingRecipe({ ...editingRecipe, ingredient: e.target.value })}
                  className="w-full border rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-orange-500"
                  rows="3"
                  required
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Thông tin dinh dưỡng
                </label>
                <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                  {NUTRITION_FIELDS.map((nutrient) => (
                    <div key={nutrient} className="flex flex-col">
                      <label className="text-sm text-gray-600 mb-1">{nutrient}</label>
                      <input
                        type="number"
                        min="0"
                        step="0.1"
                        value={parseNutrition(editingRecipe.nutrition)[nutrient] || 0}
                        onChange={(e) => {
                          const nutritionObj = parseNutrition(editingRecipe.nutrition);
                          const updatedNutrition = {
                            ...nutritionObj,
                            [nutrient]: parseFloat(e.target.value) || 0
                          };
                          setEditingRecipe({
                            ...editingRecipe,
                            nutrition: formatNutritionForApi(updatedNutrition)
                          });
                        }}
                        className="w-full border rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-orange-500"
                      />
                      <span className="text-xs text-gray-500 mt-1">{nutrient === 'calo' ? 'kcal' : 'g'}</span>
                    </div>
                  ))}
                </div>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Hình ảnh
                </label>
                <div className="space-y-2">
                  <input
                    type="file"
                    accept="image/*"
                    multiple
                    onChange={handleEditImageUpload}
                    className="block w-full text-sm text-gray-500
                      file:mr-4 file:py-2 file:px-4
                      file:rounded-full file:border-0
                      file:text-sm file:font-semibold
                      file:bg-orange-50 file:text-orange-700
                      hover:file:bg-orange-100"
                  />
                  <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-4 mt-2">
                    {editingRecipe.image?.map((img, index) => (
                      <div key={index} className="relative group">
                        <img
                          src={`data:image/jpeg;base64,${img}`}
                          alt={`Preview ${index + 1}`}
                          className="w-full h-32 object-cover rounded-lg"
                        />
                        <button
                          type="button"
                          onClick={() => handleRemoveEditImage(index)}
                          className="absolute top-1 right-1 bg-red-500 text-white p-1 rounded-full 
                            opacity-0 group-hover:opacity-100 transition-opacity"
                        >
                          <FaTimes size={12} />
                        </button>
                      </div>
                    ))}
                  </div>
                </div>
              </div>

              <div className="flex justify-end space-x-4">
                <button
                  type="button"
                  onClick={() => {
                    setShowEditForm(false);
                    setEditingRecipe(null);
                  }}
                  className="px-4 py-2 border rounded-lg hover:bg-gray-100"
                >
                  Hủy
                </button>
                <button
                  type="submit"
                  className="bg-orange-500 text-white px-4 py-2 rounded-lg hover:bg-orange-600"
                >
                  Cập nhật
                </button>
              </div>
            </form>
          </motion.div>
        </motion.div>
      )}
    </motion.div>
  );
};

export default RecipeManagement; 