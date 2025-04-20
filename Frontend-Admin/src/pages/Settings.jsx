import { useState } from 'react';
import { motion } from 'framer-motion';
import { FaSave, FaBell, FaLock, FaGlobe } from 'react-icons/fa';

const Settings = () => {
  const [settings, setSettings] = useState({
    notifications: {
      email: true,
      push: false,
      orderUpdates: true,
      promotions: false
    },
    security: {
      twoFactorAuth: false,
      sessionTimeout: 30,
      passwordExpiry: 90
    },
    language: 'vi',
    theme: 'light'
  });

  const [message, setMessage] = useState('');

  const handleSave = async () => {
    try {
      // TODO: Implement API call to save settings
      setMessage('Cài đặt đã được lưu thành công!');
      setTimeout(() => setMessage(''), 3000);
    } catch (error) {
      setMessage('Có lỗi xảy ra khi lưu cài đặt');
      setTimeout(() => setMessage(''), 3000);
    }
  };

  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      className="p-6"
    >
      <h1 className="text-2xl font-bold mb-6">Cài đặt hệ thống</h1>

      {message && (
        <div className={`mb-4 p-4 rounded ${
          message.includes('thành công') ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'
        }`}>
          {message}
        </div>
      )}

      <div className="space-y-6">
        {/* Thông báo */}
        <motion.div
          initial={{ y: 20, opacity: 0 }}
          animate={{ y: 0, opacity: 1 }}
          className="bg-white rounded-lg shadow p-6"
        >
          <div className="flex items-center mb-4">
            <FaBell className="text-orange-500 mr-2" />
            <h2 className="text-lg font-semibold">Thông báo</h2>
          </div>
          <div className="space-y-4">
            <div className="flex items-center justify-between">
              <span>Thông báo qua email</span>
              <label className="switch">
                <input
                  type="checkbox"
                  checked={settings.notifications.email}
                  onChange={(e) => setSettings({
                    ...settings,
                    notifications: {
                      ...settings.notifications,
                      email: e.target.checked
                    }
                  })}
                />
                <span className="slider round"></span>
              </label>
            </div>
            <div className="flex items-center justify-between">
              <span>Thông báo đẩy</span>
              <label className="switch">
                <input
                  type="checkbox"
                  checked={settings.notifications.push}
                  onChange={(e) => setSettings({
                    ...settings,
                    notifications: {
                      ...settings.notifications,
                      push: e.target.checked
                    }
                  })}
                />
                <span className="slider round"></span>
              </label>
            </div>
            <div className="flex items-center justify-between">
              <span>Cập nhật đơn hàng</span>
              <label className="switch">
                <input
                  type="checkbox"
                  checked={settings.notifications.orderUpdates}
                  onChange={(e) => setSettings({
                    ...settings,
                    notifications: {
                      ...settings.notifications,
                      orderUpdates: e.target.checked
                    }
                  })}
                />
                <span className="slider round"></span>
              </label>
            </div>
            <div className="flex items-center justify-between">
              <span>Khuyến mãi</span>
              <label className="switch">
                <input
                  type="checkbox"
                  checked={settings.notifications.promotions}
                  onChange={(e) => setSettings({
                    ...settings,
                    notifications: {
                      ...settings.notifications,
                      promotions: e.target.checked
                    }
                  })}
                />
                <span className="slider round"></span>
              </label>
            </div>
          </div>
        </motion.div>

        {/* Bảo mật */}
        <motion.div
          initial={{ y: 20, opacity: 0 }}
          animate={{ y: 0, opacity: 1 }}
          transition={{ delay: 0.1 }}
          className="bg-white rounded-lg shadow p-6"
        >
          <div className="flex items-center mb-4">
            <FaLock className="text-orange-500 mr-2" />
            <h2 className="text-lg font-semibold">Bảo mật</h2>
          </div>
          <div className="space-y-4">
            <div className="flex items-center justify-between">
              <span>Xác thực 2 yếu tố</span>
              <label className="switch">
                <input
                  type="checkbox"
                  checked={settings.security.twoFactorAuth}
                  onChange={(e) => setSettings({
                    ...settings,
                    security: {
                      ...settings.security,
                      twoFactorAuth: e.target.checked
                    }
                  })}
                />
                <span className="slider round"></span>
              </label>
            </div>
            <div className="flex items-center justify-between">
              <span>Thời gian timeout phiên (phút)</span>
              <select
                value={settings.security.sessionTimeout}
                onChange={(e) => setSettings({
                  ...settings,
                  security: {
                    ...settings.security,
                    sessionTimeout: parseInt(e.target.value)
                  }
                })}
                className="border rounded px-2 py-1"
              >
                <option value="15">15 phút</option>
                <option value="30">30 phút</option>
                <option value="60">1 giờ</option>
                <option value="120">2 giờ</option>
              </select>
            </div>
            <div className="flex items-center justify-between">
              <span>Thời hạn đổi mật khẩu (ngày)</span>
              <select
                value={settings.security.passwordExpiry}
                onChange={(e) => setSettings({
                  ...settings,
                  security: {
                    ...settings.security,
                    passwordExpiry: parseInt(e.target.value)
                  }
                })}
                className="border rounded px-2 py-1"
              >
                <option value="30">30 ngày</option>
                <option value="60">60 ngày</option>
                <option value="90">90 ngày</option>
                <option value="180">180 ngày</option>
              </select>
            </div>
          </div>
        </motion.div>

        {/* Ngôn ngữ và giao diện */}
        <motion.div
          initial={{ y: 20, opacity: 0 }}
          animate={{ y: 0, opacity: 1 }}
          transition={{ delay: 0.2 }}
          className="bg-white rounded-lg shadow p-6"
        >
          <div className="flex items-center mb-4">
            <FaGlobe className="text-orange-500 mr-2" />
            <h2 className="text-lg font-semibold">Ngôn ngữ và giao diện</h2>
          </div>
          <div className="space-y-4">
            <div className="flex items-center justify-between">
              <span>Ngôn ngữ</span>
              <select
                value={settings.language}
                onChange={(e) => setSettings({
                  ...settings,
                  language: e.target.value
                })}
                className="border rounded px-2 py-1"
              >
                <option value="vi">Tiếng Việt</option>
                <option value="en">English</option>
              </select>
            </div>
            <div className="flex items-center justify-between">
              <span>Giao diện</span>
              <select
                value={settings.theme}
                onChange={(e) => setSettings({
                  ...settings,
                  theme: e.target.value
                })}
                className="border rounded px-2 py-1"
              >
                <option value="light">Sáng</option>
                <option value="dark">Tối</option>
              </select>
            </div>
          </div>
        </motion.div>

        <motion.div
          initial={{ y: 20, opacity: 0 }}
          animate={{ y: 0, opacity: 1 }}
          transition={{ delay: 0.3 }}
          className="flex justify-end"
        >
          <button
            onClick={handleSave}
            className="bg-orange-500 text-white px-6 py-2 rounded-lg hover:bg-orange-600 flex items-center"
          >
            <FaSave className="mr-2" />
            Lưu cài đặt
          </button>
        </motion.div>
      </div>

      <style jsx>{`
        .switch {
          position: relative;
          display: inline-block;
          width: 60px;
          height: 34px;
        }

        .switch input {
          opacity: 0;
          width: 0;
          height: 0;
        }

        .slider {
          position: absolute;
          cursor: pointer;
          top: 0;
          left: 0;
          right: 0;
          bottom: 0;
          background-color: #ccc;
          transition: .4s;
        }

        .slider:before {
          position: absolute;
          content: "";
          height: 26px;
          width: 26px;
          left: 4px;
          bottom: 4px;
          background-color: white;
          transition: .4s;
        }

        input:checked + .slider {
          background-color: #f97316;
        }

        input:checked + .slider:before {
          transform: translateX(26px);
        }

        .slider.round {
          border-radius: 34px;
        }

        .slider.round:before {
          border-radius: 50%;
        }
      `}</style>
    </motion.div>
  );
};

export default Settings; 