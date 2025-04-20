import {FaHome, FaUserCog, FaUtensils, FaList, FaChartBar, FaPowerOff} from "react-icons/fa";
import SidebarItem from "./SidebarItem";
import {useNavigate} from "react-router-dom";
import PropTypes from 'prop-types';

const Sidebar = ({ isOpen }) => {
  const navigate = useNavigate()

  const handleLogout = () => {
    localStorage.removeItem("token")
    navigate("/login")
  }

  return (
    <div className="bg-[#12141d]">
      {/* Logo */}
      <div className={`text-center text-lg font-bold mb-6 mt-6`}>
        <span className="text-blue-500">F</span>
        <span className="text-purple-500">O</span>
        <span className="text-red-500">OD</span>
      </div>
      <div
        className={`h-[90vh] bg-[#12141d] text-white flex p-4 transition-all duration-300 ${
          isOpen ? "w-56" : "w-20"
        }`}
      >
        {/* Sidebar Menu */}
        <nav className="flex flex-col space-y-2">
          <SidebarItem icon={FaHome} label="Trang chủ" to="/" isOpen={isOpen} />
          <SidebarItem icon={FaUserCog} label="Quản lý người dùng" to="/user-management" isOpen={isOpen} />
          <SidebarItem icon={FaUtensils} label="Quản lý công thức" to="/recipe-management" isOpen={isOpen} />
          <SidebarItem icon={FaList} label="Quản lý loại món" to="/category-management" isOpen={isOpen} />
          <SidebarItem icon={FaChartBar} label="Thống kê yêu thích" to="/favorite-recipes" isOpen={isOpen} />
          <div className="cursor-pointer flex justify-center pt-2 pb-2 bg-red-500 rounded-lg" onClick={handleLogout}>
            <FaPowerOff className="mt-1" />
          </div>
        </nav>
      </div>
    </div>
  );
};

Sidebar.propTypes = {
  isOpen: PropTypes.bool.isRequired
};

export default Sidebar;
