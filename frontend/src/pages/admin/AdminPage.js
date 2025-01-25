import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '@styles/AdminPage.css';
import UserManagement from '@components/admin/UserManagement'; // UserManagement 컴포넌트 import
import AdEmailExtract from '@components/admin/AdEmailExtract';

const AdminPage = ( ) => {
  const [SelectedComponent, setSelectedComponent] = useState(() => () => <div>기본 화면</div>);
  const navigate = useNavigate();

  return (
    <div className="admin-page flex">
      {/* Sidebar */}
      <aside className="admin-sidebar bg-gray-200 p-4 w-1/4 h-screen">
        <h2 className="text-lg font-bold mb-4">관리자 메뉴</h2>
        <ul>
          <li className="dropdown mb-2">
            <span className="cursor-pointer font-medium">사용자 관리</span>
            <ul className="dropdown-content pl-4 mt-2 space-y-2">
              <li onClick={() => setSelectedComponent(() => UserManagement)} className="cursor-pointer hover:text-blue-500">
                사용자 관리
              </li>
              <li onClick={() => setSelectedComponent(() => AdEmailExtract)} className="cursor-pointer hover:text-blue-500">
                광고수신동의 이메일 추출
              </li>
            </ul>
          </li>
          <li
            onClick={() => navigate('/admin/products')}
            className="cursor-pointer mb-2 hover:text-blue-500 font-medium"
          >
            제품 관리
          </li>
          <li
            onClick={() => navigate('/admin/faq')}
            className="cursor-pointer hover:text-blue-500 font-medium"
          >
            FAQ 관리
          </li>
        </ul>
      </aside>

      {/* Main Content */}
      <main className="admin-content flex-1 p-6">
        <h1 className="text-2xl font-bold mb-4">관리자 페이지</h1>
        {/* <p className="mb-6">관리자 {userId}님, 환영합니다!</p> */}
        <div className="content">
          {/* Selected Component */}
          <SelectedComponent />
        </div>
      </main>
    </div>
  );
};

export default AdminPage;
