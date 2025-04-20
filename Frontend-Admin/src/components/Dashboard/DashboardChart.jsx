import React from "react";
import { Bar, Pie, Line } from "react-chartjs-2";
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, ArcElement, LineElement, PointElement } from "chart.js";

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, ArcElement, LineElement, PointElement);

const DashboardChart = ({ type }) => {
  const data = {
    labels: ["Label 1", "Label 2", "Label 3"],
    datasets: [
      {
        label: "Hoàn thành",
        data: [30, 45, 60],
        backgroundColor: ["#6366F1", "#EC4899", "#10B981"],
      },
    ],
  };

  return (
    <div className="bg-white shadow-md rounded-lg p-5 w-80 h-64 flex justify-center items-center">
      {type === "bar" && <Bar data={data} />}
      {type === "pie" && <Pie data={data} />}
      {type === "line" && <Line data={data} />}
    </div>
  );
};

export default DashboardChart;
