import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

// Hàm chuyển đổi base64 thành mảng byte
const base64ToByteArray = (base64String) => {
  const binaryString = atob(base64String);
  const bytes = new Uint8Array(binaryString.length);
  for (let i = 0; i < binaryString.length; i++) {
    bytes[i] = binaryString.charCodeAt(i);
  }
  return Array.from(bytes);
};

// lấy ra tất cả các loại món ăn
export const getCategories = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/typefood`);
        return response.data;
    } catch (error) {
        throw error.response?.data || error.message;
    }
};

// thêm loại món ăn
export const addCategory = async (category) => {
    try {
        // Lấy danh sách loại món ăn hiện tại
        const categories = await getCategories();
        
        // Tìm id lớn nhất và tăng thêm 1
        const maxId = categories.length > 0 
            ? Math.max(...categories.map(cat => cat.id)) 
            : 0;
        const newId = maxId + 1;

        // Chuyển đổi ảnh từ base64 sang mảng byte
        const processedData = {
            ...category,
            id: newId,
            img: category.imageBase64 ? base64ToByteArray(category.imageBase64) : null
        };
        const response = await axios.post(`${API_BASE_URL}/typefood`, processedData);
        return response.data;
    } catch (error) {
        throw error.response?.data || error.message;
    }
};

// cập nhật loại món ăn
export const updateCategory = async (categoryId, category) => {
    try {
        // Chuyển đổi ảnh từ base64 sang mảng byte
        const processedData = {
            ...category,
            img: category.imageBase64 ? base64ToByteArray(category.imageBase64) : null
        };
        const response = await axios.patch(`${API_BASE_URL}/typefood/${categoryId}`, processedData);
        return response.data;
    } catch (error) {
        throw error.response?.data || error.message;
    }
};

// xóa loại món ăn
export const deleteCategory = async (categoryId) => {
    try {
        const response = await axios.delete(`${API_BASE_URL}/typefood/${categoryId}`);
        return response.data;
    } catch (error) {
        throw error.response?.data || error.message;
    }
};  
