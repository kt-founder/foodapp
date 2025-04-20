import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

export const getFavorites = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/favorites`);
        return response.data;
    } catch (error) {
        throw error.response?.data || error.message;
    }
};

export const addFavorite = async (favoriteData) => {
    try {
        const response = await axios.post(`${API_BASE_URL}/favorites`, favoriteData);
        return response.data;
    } catch (error) {
        throw error.response?.data || error.message;
    }
};  


