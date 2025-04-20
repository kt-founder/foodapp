import { useState, useEffect } from 'react';
import { FaTrash, FaEdit, FaPlus, FaTimes } from 'react-icons/fa';
import { motion, AnimatePresence } from 'framer-motion';
import { getCategories, addCategory, updateCategory, deleteCategory } from '../api/typefood';

const CategoryManagement = () => {
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [showAddForm, setShowAddForm] = useState(false);
  const [showEditForm, setShowEditForm] = useState(false);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [newCategory, setNewCategory] = useState({
    name: '',
    imageBase64: null
  });
  const [editImageBase64, setEditImageBase64] = useState(null);

  useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = async () => {
    try {
      const data = await getCategories();
      setCategories(data);
      setLoading(false);
    } catch (err) {
      setError('Không thể tải danh sách loại món: ' + err.message);
      setLoading(false);
    }
  };

  const handleAddCategory = async (e) => {
    e.preventDefault();
    try {
      await addCategory(newCategory);
      setShowAddForm(false);
      setNewCategory({ name: '', imageBase64: null });
      fetchCategories();
    } catch (err) {
      setError('Không thể thêm loại món mới: ' + err.message);
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
    const file = e.target.files[0];
    if (file) {
      try {
        const base64String = await convertToBase64(file);
        setNewCategory({
          ...newCategory,
          imageBase64: base64String
        });
      } catch (err) {
        setError('Không thể tải ảnh lên: ' + err.message);
      }
    }
  };

  const handleEditImageUpload = async (e) => {
    const file = e.target.files[0];
    if (file) {
      try {
        const base64String = await convertToBase64(file);
        setEditImageBase64(base64String);
      } catch (err) {
        setError('Không thể tải ảnh lên: ' + err.message);
      }
    }
  };

  const handleEditClick = (category) => {
    setSelectedCategory(category);
    setEditImageBase64(category.imageBase64);
    setShowEditForm(true);
  };

  const handleEditCategory = async (e) => {
    e.preventDefault();
    try {
      const updatedCategory = {
        ...selectedCategory,
        imageBase64: editImageBase64
      };
      await updateCategory(selectedCategory.id, updatedCategory);
      setShowEditForm(false);
      setSelectedCategory(null);
      setEditImageBase64(null);
      fetchCategories();
    } catch (err) {
      setError('Không thể cập nhật loại món: ' + err.message);
    }
  };

  const handleDeleteCategory = async (categoryId) => {
    if (window.confirm('Bạn có chắc chắn muốn xóa loại món này?')) {
      try {
        await deleteCategory(categoryId);
        fetchCategories();
      } catch (err) {
        setError('Không thể xóa loại món: ' + err.message);
      }
    }
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
        <h1 className="text-2xl font-bold">Quản lý loại món</h1>
        <button
          onClick={() => setShowAddForm(true)}
          className="bg-orange-500 text-white px-4 py-2 rounded-lg hover:bg-orange-600 flex items-center"
        >
          <FaPlus className="mr-2" />
          Thêm loại món
        </button>
      </div>

      {error && (
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
          {error}
        </div>
      )}

      {/* Form thêm loại món mới */}
      <AnimatePresence>
        {showAddForm && (
          <motion.div
            initial={{ opacity: 0, y: -20 }}
            animate={{ opacity: 1, y: 0 }}
            exit={{ opacity: 0, y: -20 }}
            className="bg-white rounded-lg shadow p-6 mb-6"
          >
            <div className="flex justify-between items-center mb-4">
              <h2 className="text-lg font-semibold">Thêm loại món mới</h2>
              <button
                onClick={() => setShowAddForm(false)}
                className="text-gray-500 hover:text-gray-700"
              >
                <FaTimes />
              </button>
            </div>
            <form onSubmit={handleAddCategory} className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Tên loại món
                </label>
                <input
                  type="text"
                  value={newCategory.name}
                  onChange={(e) => setNewCategory({ ...newCategory, name: e.target.value })}
                  className="w-full border rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-orange-500"
                  required
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Ảnh loại món
                </label>
                <div className="flex items-center space-x-4">
                  {newCategory.imageBase64 && (
                    <img
                      src={`data:image/jpeg;base64,${newCategory.imageBase64}`}
                      alt="Preview"
                      className="h-20 w-20 object-cover rounded-lg"
                    />
                  )}
                  <input
                    type="file"
                    accept="image/*"
                    onChange={handleImageUpload}
                    className="block w-full text-sm text-gray-500
                      file:mr-4 file:py-2 file:px-4
                      file:rounded-full file:border-0
                      file:text-sm file:font-semibold
                      file:bg-orange-50 file:text-orange-700
                      hover:file:bg-orange-100"
                  />
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
      </AnimatePresence>

      {/* Form chỉnh sửa loại món */}
      <AnimatePresence>
        {showEditForm && selectedCategory && (
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
              className="bg-white rounded-lg shadow-lg p-6 w-full max-w-md"
            >
              <div className="flex justify-between items-center mb-4">
                <h2 className="text-lg font-semibold">Sửa loại món</h2>
                <button
                  onClick={() => {
                    setShowEditForm(false);
                    setSelectedCategory(null);
                    setEditImageBase64(null);
                  }}
                  className="text-gray-500 hover:text-gray-700"
                >
                  <FaTimes />
                </button>
              </div>
              <form onSubmit={handleEditCategory} className="space-y-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">
                    Tên loại món
                  </label>
                  <input
                    type="text"
                    value={selectedCategory.name}
                    onChange={(e) => setSelectedCategory({ ...selectedCategory, name: e.target.value })}
                    className="w-full border rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-orange-500"
                    required
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">
                    Ảnh loại món
                  </label>
                  <div className="flex items-center space-x-4">
                    {editImageBase64 && (
                      <img
                        src={`data:image/jpeg;base64,${editImageBase64}`}
                        alt="Preview"
                        className="h-20 w-20 object-cover rounded-lg"
                      />
                    )}
                    <input
                      type="file"
                      accept="image/*"
                      onChange={handleEditImageUpload}
                      className="block w-full text-sm text-gray-500
                        file:mr-4 file:py-2 file:px-4
                        file:rounded-full file:border-0
                        file:text-sm file:font-semibold
                        file:bg-orange-50 file:text-orange-700
                        hover:file:bg-orange-100"
                    />
                  </div>
                </div>
                <div className="flex justify-end space-x-4">
                  <button
                    type="button"
                    onClick={() => {
                      setShowEditForm(false);
                      setSelectedCategory(null);
                      setEditImageBase64(null);
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
      </AnimatePresence>

      {/* Danh sách loại món */}
      <div className="bg-white rounded-lg shadow overflow-hidden">
        <table className="min-w-full">
          <thead className="bg-gray-50">
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Tên loại món
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Ảnh
              </th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                Thao tác
              </th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {categories.map((category) => (
              <motion.tr
                key={category.id}
                initial={{ opacity: 0, y: 20 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration: 0.3 }}
              >
                <td className="px-6 py-4 whitespace-nowrap">
                  <div className="text-sm font-medium text-gray-900">{category.name}</div>
                </td>
                <td className="px-6 py-4 whitespace-nowrap">
                  {category.imageBase64 && (
                    <img
                      src={`data:image/jpeg;base64,${category.imageBase64}`}
                      alt={category.name}
                      className="h-12 w-12 rounded-lg object-cover"
                    />
                  )}
                </td>
                <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                  <button
                    onClick={() => handleEditClick(category)}
                    className="text-yellow-600 hover:text-yellow-900 mr-4"
                  >
                    <FaEdit />
                  </button>
                </td>
              </motion.tr>
            ))}
          </tbody>
        </table>
      </div>
    </motion.div>
  );
};

export default CategoryManagement; 