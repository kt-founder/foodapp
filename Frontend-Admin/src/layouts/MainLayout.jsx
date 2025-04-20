import React, { useState } from "react";
import Sidebar from "../components/Sidebar/Sidebar";
import { FaBars } from "react-icons/fa";
import { Link } from "react-router-dom";

const MainLayout = ({ children }) => {
  const [isOpen, setIsOpen] = useState(true); // State để kiểm soát Sidebar

  return (
    <div className="flex h-screen bg-gray-100">
      {/* Sidebar */}
      <Sidebar isOpen={isOpen} setIsOpen={setIsOpen} />

      {/* Nội dung chính */}
      <div className="flex-1 flex flex-col">
        {/* Header */}
        <header className="bg-white p-4 shadow-md flex justify-between items-center">
          <div className="p-4 bg-white flex items-center">
            <button
              className="text-gray-700 text-2xl p-2 rounded-lg hover:bg-gray-200 transition"
              onClick={() => setIsOpen(!isOpen)}
            >
              <FaBars />
            </button>
          </div>
          <div className="flex space-x-4 ml-auto">
            <button className="bg-blue-500 text-white p-2 rounded-full shadow-md hover:shadow-lg transition duration-300">🔔</button>
            <Link to="/" className="bg-blue-500 text-white p-2 rounded-full shadow-md hover:shadow-lg transition duration-300">👤</Link>
          </div>
        </header>

        {/* Nội dung chính */}
        <main className="flex-1 overflow-auto bg-white">{children}</main>
      </div>
    </div>
  );
};

export default MainLayout;
