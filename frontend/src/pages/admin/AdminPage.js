import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import UseAdmin from "@components/login/UseAdmin";
import "@styles/AdminPage.css";

// 사용자 관리 컴포넌트 (예시)
const UserView = () => (
  <>
    <h2>사용자 조회</h2>
    <p>여기에서 사용자 목록을 조회할 수 있습니다.</p>
  </>
);

const UserAdd = () => (
  <>
    <h2>사용자 추가</h2>
    <p>새로운 사용자를 추가할 수 있습니다.</p>
  </>
);

const UserEdit = () => (
  <>
    <h2>사용자 수정</h2>
    <p>사용자 정보를 수정할 수 있습니다.</p>
  </>
);

const UserDelete = () => (
  <>
    <h2>사용자 삭제</h2>
    <p>사용자를 삭제할 수 있습니다.</p>
  </>
);

const AdminPage = () => {
  const navigate = useNavigate();
  const { userId, role } = UseAdmin();
  const [SelectedComponent, setSelectedComponent] = useState(() => UserView); // 기본으로 UserView 표시

  // 관리자 권한 확인
  if (role !== "ROLE_ADMIN") {
    alert("접근 권한이 없습니다."); // 경고 메시지
    navigate("/"); // 메인 페이지로 리다이렉트
    return null; // 페이지 렌더링 중단
  }

  return (
    <div className="admin-page">
      <aside className="admin-sidebar">
        <h2>관리자 메뉴</h2>
        <ul>
          <li className="dropdown">
            사용자 관리
            <ul className="dropdown-content">
              <li onClick={() => setSelectedComponent(() => UserView)}>사용자 조회</li>
              <li onClick={() => setSelectedComponent(() => UserAdd)}>사용자 추가</li>
              <li onClick={() => setSelectedComponent(() => UserEdit)}>사용자 수정</li>
              <li onClick={() => setSelectedComponent(() => UserDelete)}>사용자 삭제</li>
            </ul>
          </li>
          <li onClick={() => navigate("/admin/products")}>제품 관리</li>
          <li onClick={() => navigate("/admin/faq")}>FAQ 관리</li>
        </ul>
      </aside>
      <main className="admin-content">
        <h1>관리자 페이지</h1>
        <p>관리자 {userId}님, 환영합니다!</p>
        {/* 선택된 컴포넌트를 렌더링 */}
        <SelectedComponent />
      </main>
    </div>
  );
};

export default AdminPage;
