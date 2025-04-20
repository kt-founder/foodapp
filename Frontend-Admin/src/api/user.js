import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

export const registerUser = async (data) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/users/register`, data);
    return response.data;
  } catch (error) {
    throw error.response?.data || error.message;
  }
};

export const loginUser = async (data) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/users/login`, data);
    return response.data;
  } catch (error) {
    throw error.response?.data || error.message;
  }
};
export const getUsers = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/users`);
        return response.data;
    } catch (error) {
        throw error.response?.data || error.message;
    }
}
// thêm user
export const addUser = async (data) => {
    try {
        const response = await axios.post(`${API_BASE_URL}/users/add`, data);
        return response.data;
    } catch (error) {
        throw error.response?.data || error.message;
    }
}
//change password
export const changePassword = async (data, id) => {
    try {
        const response = await axios.post(`${API_BASE_URL}/users/${id}/password`, data);
        return response.data;
    } catch (error) {
        throw error.response?.data || error.message;
    }
}   
