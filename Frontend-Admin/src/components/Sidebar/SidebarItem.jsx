import { Link, useLocation } from "react-router-dom";

const SidebarItem = ({ icon: Icon, label, to, isOpen }) => {
  const location = useLocation();
  const isActive = location.pathname === to;

  return (
    <Link
      to={to}
      className={`flex items-center gap-3 px-4 py-2 text-sm rounded-md cursor-pointer transition-all ${
        isActive
          ? "bg-gradient-to-r from-blue-800 to-blue-500 text-white"
          : "text-white hover:bg-gray-700"
      }`}
    >
      <Icon
        className={`text-lg transition-all ${
          isActive ? "text-white" : "text-white hover:text-blue-400"
        }`}
      />
      {/* Hiển thị label khi Sidebar mở */}
      {isOpen && <span>{label}</span>}
    </Link>
  );
};

export default SidebarItem;
