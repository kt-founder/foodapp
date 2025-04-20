import { useState, useEffect } from 'react';
import { motion } from 'framer-motion';
import { FaUsers, FaUtensils, FaEye, FaHeart } from 'react-icons/fa';
import { Line, Bar, Doughnut } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  BarElement,
  ArcElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import { getStatistics } from '../api/statistics';
import { getRecipes } from '../api/recipe';
import { getUsers } from '../api/user';
import { getCategories } from '../api/typefood';

// Đăng ký các components của Chart.js
ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  BarElement,
  ArcElement,
  Title,
  Tooltip,
  Legend
);

const Dashboard = () => {
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [stats, setStats] = useState(null);
  const [recipes, setRecipes] = useState([]);
  const [users, setUsers] = useState([]);
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const [statsData, recipesData, usersData, categoriesData] = await Promise.all([
        getStatistics(),
        getRecipes(),
        getUsers(),
        getCategories()
      ]);
      setStats(statsData);
      setRecipes(recipesData);
      setUsers(usersData);
      setCategories(categoriesData);
      setLoading(false);
    } catch (err) {
      setError('Không thể tải dữ liệu: ' + err.message);
      setLoading(false);
    }
  };

  const containerVariants = {
    hidden: { opacity: 0 },
    visible: {
      opacity: 1,
      transition: {
        staggerChildren: 0.1
      }
    }
  };

  const itemVariants = {
    hidden: { y: 20, opacity: 0 },
    visible: {
      y: 0,
      opacity: 1,
      transition: {
        type: "spring",
        stiffness: 100
      }
    }
  };

  // Tính toán các thống kê
  const totalViews = recipes.reduce((sum, recipe) => sum + (recipe.views || 0), 0);
  const totalLikes = recipes.reduce((sum, recipe) => sum + (recipe.likes || 0), 0);
  const averageLikes = recipes.length > 0 ? (totalLikes / recipes.length).toFixed(1) : 0;
  const averageViews = recipes.length > 0 ? (totalViews / recipes.length).toFixed(1) : 0;

  // Dữ liệu cho biểu đồ phân bố món ăn theo loại
  const categoryData = {
    labels: categories.map(cat => cat.name),
    datasets: [
      {
        data: categories.map(cat => 
          recipes.filter(recipe => recipe.typeFoodId === cat.id).length
        ),
        backgroundColor: [
          '#FF6384',
          '#36A2EB',
          '#FFCE56',
          '#4BC0C0',
          '#9966FF',
          '#FF9F40',
          '#FF6384',
          '#36A2EB'
        ],
        borderWidth: 1
      }
    ]
  };

  // Dữ liệu cho biểu đồ top món ăn được xem nhiều nhất
  const topViewedRecipes = [...recipes]
    .sort((a, b) => (b.views || 0) - (a.views || 0))
    .slice(0, 5);

  const topViewedData = {
    labels: topViewedRecipes.map(recipe => recipe.name),
    datasets: [
      {
        label: 'Lượt xem',
        data: topViewedRecipes.map(recipe => recipe.views || 0),
        backgroundColor: 'rgba(255, 99, 132, 0.5)',
        borderColor: 'rgba(255, 99, 132, 1)',
        borderWidth: 1
      }
    ]
  };

  // Dữ liệu cho biểu đồ top món ăn được yêu thích nhất
  const topLikedRecipes = [...recipes]
    .sort((a, b) => (b.likes || 0) - (a.likes || 0))
    .slice(0, 5);

  const topLikedData = {
    labels: topLikedRecipes.map(recipe => recipe.name),
    datasets: [
      {
        label: 'Lượt thích',
        data: topLikedRecipes.map(recipe => recipe.likes || 0),
        backgroundColor: 'rgba(54, 162, 235, 0.5)',
        borderColor: 'rgba(54, 162, 235, 1)',
        borderWidth: 1
      }
    ]
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center h-screen bg-gray-50">
        <div className="text-center">
          <div className="animate-spin rounded-full h-16 w-16 border-t-4 border-b-4 border-orange-500 mx-auto"></div>
          <p className="mt-4 text-gray-600">Đang tải dữ liệu...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="p-6">
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative" role="alert">
          <strong className="font-bold">Lỗi!</strong>
          <span className="block sm:inline"> {error}</span>
        </div>
      </div>
    );
  }

  return (
    <motion.div
      variants={containerVariants}
      initial="hidden"
      animate="visible"
      className="p-6 bg-gray-50 min-h-screen"
    >
      <h1 className="text-3xl font-bold mb-8 text-gray-800">Dashboard</h1>

      {/* Thống kê tổng quan */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        <motion.div
          variants={itemVariants}
          className="bg-white rounded-xl shadow-lg p-6 hover:shadow-xl transition-shadow duration-300"
        >
          <div className="flex items-center">
            <div className="p-4 rounded-full bg-blue-100 text-blue-500">
              <FaUsers className="w-8 h-8" />
            </div>
            <div className="ml-4">
              <p className="text-sm text-gray-500">Tổng người dùng</p>
              <p className="text-2xl font-bold text-gray-800">{users.length}</p>
            </div>
          </div>
        </motion.div>

        <motion.div
          variants={itemVariants}
          className="bg-white rounded-xl shadow-lg p-6 hover:shadow-xl transition-shadow duration-300"
        >
          <div className="flex items-center">
            <div className="p-4 rounded-full bg-green-100 text-green-500">
              <FaUtensils className="w-8 h-8" />
            </div>
            <div className="ml-4">
              <p className="text-sm text-gray-500">Tổng món ăn</p>
              <p className="text-2xl font-bold text-gray-800">{recipes.length}</p>
            </div>
          </div>
        </motion.div>

        <motion.div
          variants={itemVariants}
          className="bg-white rounded-xl shadow-lg p-6 hover:shadow-xl transition-shadow duration-300"
        >
          <div className="flex items-center">
            <div className="p-4 rounded-full bg-yellow-100 text-yellow-500">
              <FaEye className="w-8 h-8" />
            </div>
            <div className="ml-4">
              <p className="text-sm text-gray-500">Lượt xem trung bình</p>
              <p className="text-2xl font-bold text-gray-800">{averageViews}</p>
            </div>
          </div>
        </motion.div>

        <motion.div
          variants={itemVariants}
          className="bg-white rounded-xl shadow-lg p-6 hover:shadow-xl transition-shadow duration-300"
        >
          <div className="flex items-center">
            <div className="p-4 rounded-full bg-red-100 text-red-500">
              <FaHeart className="w-8 h-8" />
            </div>
            <div className="ml-4">
              <p className="text-sm text-gray-500">Lượt thích trung bình</p>
              <p className="text-2xl font-bold text-gray-800">{averageLikes}</p>
            </div>
          </div>
        </motion.div>
      </div>

      {/* Biểu đồ */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
        <motion.div
          variants={itemVariants}
          className="bg-white rounded-xl shadow-lg p-6 hover:shadow-xl transition-shadow duration-300"
        >
          <h2 className="text-xl font-bold mb-6 text-gray-800">Phân bố món ăn theo loại</h2>
          <div className="h-[300px] flex items-center justify-center">
            <Doughnut 
              data={categoryData}
              options={{
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                  legend: {
                    position: 'right',
                    labels: {
                      font: {
                        size: 12
                      }
                    }
                  }
                }
              }}
            />
          </div>
        </motion.div>

        <motion.div
          variants={itemVariants}
          className="bg-white rounded-xl shadow-lg p-6 hover:shadow-xl transition-shadow duration-300"
        >
          <h2 className="text-xl font-bold mb-6 text-gray-800">Top món ăn được xem nhiều nhất</h2>
          <div className="h-[300px] flex items-center justify-center">
            <Bar 
              data={topViewedData}
              options={{
                responsive: true,
                maintainAspectRatio: false,
                indexAxis: 'y',
                plugins: {
                  legend: {
                    display: false
                  }
                },
                scales: {
                  x: {
                    beginAtZero: true,
                    grid: {
                      display: false
                    }
                  },
                  y: {
                    grid: {
                      display: false
                    }
                  }
                }
              }}
            />
          </div>
        </motion.div>

        <motion.div
          variants={itemVariants}
          className="bg-white rounded-xl shadow-lg p-6 hover:shadow-xl transition-shadow duration-300"
        >
          <h2 className="text-xl font-bold mb-6 text-gray-800">Top món ăn được yêu thích nhất</h2>
          <div className="h-[300px] flex items-center justify-center">
            <Bar 
              data={topLikedData}
              options={{
                responsive: true,
                maintainAspectRatio: false,
                indexAxis: 'y',
                plugins: {
                  legend: {
                    display: false
                  }
                },
                scales: {
                  x: {
                    beginAtZero: true,
                    grid: {
                      display: false
                    }
                  },
                  y: {
                    grid: {
                      display: false
                    }
                  }
                }
              }}
            />
          </div>
        </motion.div>

        <motion.div
          variants={itemVariants}
          className="bg-white rounded-xl shadow-lg p-6 hover:shadow-xl transition-shadow duration-300"
        >
          <h2 className="text-xl font-bold mb-6 text-gray-800">Chi tiết top món ăn được yêu thích</h2>
          <div className="space-y-4">
            {topLikedRecipes.map((recipe, index) => (
              <div key={recipe.id} className="flex items-center p-4 bg-gray-50 rounded-lg hover:bg-gray-100 transition-colors duration-200">
                <span className="w-8 h-8 flex items-center justify-center bg-orange-100 text-orange-500 rounded-full font-bold">
                  {index + 1}
                </span>
                <div className="ml-4 flex-1">
                  <p className="font-medium text-gray-800">{recipe.name}</p>
                  <div className="flex items-center space-x-4 text-sm text-gray-500">
                    <span className="flex items-center">
                      <FaHeart className="mr-1 text-red-500" />
                      {recipe.likes || 0}
                    </span>
                    <span className="flex items-center">
                      <FaEye className="mr-1 text-blue-500" />
                      {recipe.views || 0}
                    </span>
                  </div>
                </div>
                {recipe.imageBase64 && (
                  <img
                    src={`data:image/jpeg;base64,${recipe.imageBase64}`}
                    alt={recipe.name}
                    className="w-12 h-12 rounded-lg object-cover"
                  />
                )}
              </div>
            ))}
          </div>
        </motion.div>
      </div>
    </motion.div>
  );
};

export default Dashboard;