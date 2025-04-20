import { createBrowserRouter } from 'react-router-dom';
import Login from '../pages/Login';
import Register from '../pages/Register';
import ProtectedRoute from '../components/ProtectedRoute';
import UserManagement from '../pages/UserManagement';
import RecipeManagement from '../pages/RecipeManagement';
import CategoryManagement from '../pages/CategoryManagement';
import FavoriteRecipes from '../pages/FavoriteRecipes';

const router = createBrowserRouter([
  {
    path: '/login',
    element: <Login />
  },
  {
    path: '/register',
    element: <Register />
  },
  {
    path: '/user-management',
    element: (
      <ProtectedRoute>
        <UserManagement />
      </ProtectedRoute>
    )
  },
  {
    path: '/recipe-management',
    element: (
      <ProtectedRoute>
        <RecipeManagement />
      </ProtectedRoute>
    )
  },
  {
    path: '/category-management',
    element: (
      <ProtectedRoute>
        <CategoryManagement />
      </ProtectedRoute>
    )
  },
  {
    path: '/favorite-recipes',
    element: (
      <ProtectedRoute>
        <FavoriteRecipes />
      </ProtectedRoute>
    )
  }
]);

export default router; 