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

export const getRecipes = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/food`);
        return response.data;
    } catch (error) {
        throw error.response?.data || error.message;
    }
};

export const addRecipe = async (recipeData) => {
    try {
        // Chuyển đổi ảnh từ base64 sang mảng byte
        const processedData = {
            ...recipeData,
            image: recipeData.image ? base64ToByteArray(recipeData.image) : null
        };
        const response = await axios.post(`${API_BASE_URL}/food`, processedData);
        return response.data;
    } catch (error) {
        throw error.response?.data || error.message;
    }
};

export const deleteRecipe = async (recipeId) => {
    try {
        const response = await axios.delete(`${API_BASE_URL}/food/${recipeId}`);
        return response.data;
    } catch (error) {
        throw error.response?.data || error.message;
    }
};

export const updateRecipe = async (recipeId, recipeData) => {
    try {
        // Chuyển đổi ảnh từ base64 sang mảng byte
        const processedData = {
            ...recipeData,
            image: recipeData.image ? base64ToByteArray(recipeData.image) : null
        };
        const response = await axios.patch(`${API_BASE_URL}/food/${recipeId}`, processedData);
        return response.data;
    } catch (error) {
        throw error.response?.data || error.message;
    }
};