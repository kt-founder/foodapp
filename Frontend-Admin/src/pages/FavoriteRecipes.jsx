import { useState, useEffect } from 'react';
import axios from 'axios';
import { motion } from 'framer-motion';
import { FaHeart, FaChartLine } from 'react-icons/fa';

const FavoriteRecipes = () => {
  const [favorites, setFavorites] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [timeRange, setTimeRange] = useState('week'); // week, month, year

  useEffect(() => {
    fetchFavorites();
  }, [timeRange]);

  const fetchFavorites = async () => {
    try {
      const response = await axios.get(`${import.meta.env.VITE_API_BASE_URL}/recipes/favorites?timeRange=${timeRange}`);
      setFavorites(response.data);
      setLoading(false);
    } catch (err) {
      setLoading(false);
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
        <h1 className="text-2xl font-bold">Thống kê món ăn yêu thích</h1>
        <div className="flex items-center space-x-4">
          <select
            value={timeRange}
            onChange={(e) => setTimeRange(e.target.value)}
            className="border rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-orange-500"
          >
            <option value="week">7 ngày qua</option>
            <option value="month">30 ngày qua</option>
            <option value="year">12 tháng qua</option>
          </select>
        </div>
      </div>

      {error && (
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
          {error}
        </div>
      )}

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {favorites.map((recipe, index) => (
          <motion.div
            key={recipe.id}
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: index * 0.1 }}
            className="bg-white rounded-lg shadow-lg overflow-hidden"
          >
            <div className="relative">
              <img
                src={recipe.image}
                alt={recipe.name}
                className="w-full h-48 object-cover"
              />
              <div className="absolute top-2 right-2 bg-red-500 text-white px-2 py-1 rounded-full text-sm flex items-center">
                <FaHeart className="mr-1" />
                {recipe.likes}
              </div>
            </div>
            <div className="p-4">
              <h3 className="text-lg font-semibold mb-2">{recipe.name}</h3>
              <p className="text-gray-600 text-sm mb-4">{recipe.description}</p>
              <div className="flex items-center justify-between">
                <div className="flex items-center text-gray-500">
                  <FaChartLine className="mr-1" />
                  <span className="text-sm">Lượt xem: {recipe.views}</span>
                </div>
                <span className="text-sm text-gray-500">
                  {recipe.category}
                </span>
              </div>
            </div>
          </motion.div>
        ))}
      </div>

      {favorites.length === 0 && (
        <div className="text-center py-12">
          <FaHeart className="mx-auto text-4xl text-gray-400 mb-4" />
          <p className="text-gray-500">Chưa có món ăn nào được yêu thích trong khoảng thời gian này</p>
        </div>
      )}
    </motion.div>
  );
};

export default FavoriteRecipes; 