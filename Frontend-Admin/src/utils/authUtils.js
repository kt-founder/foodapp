// src/utils/authUtils.js

export const checkAuth = (navigate) => {
    const token = localStorage.getItem("token");

    if (!token) {
        navigate("/login");
    }

    return token;
};
