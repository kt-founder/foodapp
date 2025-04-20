import { createBrowserRouter, RouterProvider } from "react-router-dom";

// Layout
import MainLayout from "../layouts/MainLayout";
import Dashboard from "../pages/Dashboard";
import Login from "../pages/Login";
import UserManagement from "../pages/UserManagement";
import RecipeManagement from "../pages/RecipeManagement";
import CategoryManagement from "../pages/CategoryManagement";
import Settings from "../pages/Settings";
import FavoriteRecipes from "../pages/FavoriteRecipes";
import ProtectedRoute from "../components/ProtectedRoute";

// Cấu hình routes
const routes = [
  {
    path: "/", 
    element: (
      <ProtectedRoute>
        <MainLayout>
          <Dashboard />
        </MainLayout>
      </ProtectedRoute>
    ),
  },
  {
    path: "/login",
    element: <Login />,
  },
  {
    path: "/user-management",
    element: (
      <ProtectedRoute>
        <MainLayout>
          <UserManagement />
        </MainLayout>
      </ProtectedRoute>
    ),
  },
  {
    path: "/recipe-management",
    element: (
      <ProtectedRoute>
        <MainLayout>
          <RecipeManagement />
        </MainLayout>
      </ProtectedRoute>
    ),
  },
  {
    path: "/category-management",
    element: (
      <ProtectedRoute>
        <MainLayout>
          <CategoryManagement />
        </MainLayout>
      </ProtectedRoute>
    ),
  },
  {
    path: "/favorite-recipes",
    element: (
      <ProtectedRoute>
        <MainLayout>
          <FavoriteRecipes />
        </MainLayout>
      </ProtectedRoute>
    ),
  },
  {
    path: "/settings",
    element: (
      <ProtectedRoute>
        <MainLayout>
          <Settings />
        </MainLayout>
      </ProtectedRoute>
    ),
  },
];

const AppRoutes = () => {
  const router = createBrowserRouter(routes);
  return <RouterProvider router={router} />;
};

export default AppRoutes;
