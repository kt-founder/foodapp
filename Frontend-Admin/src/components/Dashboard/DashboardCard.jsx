import React from "react";
import { FaRegFolderOpen } from "react-icons/fa";

const DashboardCard = ({ title, value, description }) => {
  return (
    <div className="bg-white shadow-md rounded-lg p-5 flex flex-col items-start w-64">
      <div className="bg-blue-500 text-white p-3 rounded-full mb-3">
        <FaRegFolderOpen size={20} />
      </div>
      <h2 className="text-lg font-bold text-gray-600">{title}</h2>
      <p className="text-2xl font-extrabold text-blue-700">{value}</p>
      <p className="text-gray-500 text-sm">{description}</p>
    </div>
  );
};

export default DashboardCard;
