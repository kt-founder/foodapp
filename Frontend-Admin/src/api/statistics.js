import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

//call api statistics/all 
export const getStatistics = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/statistics/all`);
        return response.data;
    } catch (error) {
        throw error.response?.data || error.message;
    }
}   

// call api favorites
export const getFavorites = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/favorites`);
        return response.data;
    } catch (error) {
        throw error.response?.data || error.message;
    }   
}

// call api categories
