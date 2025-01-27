import React, { useState } from 'react';
import '@styles/UserManagement.css';

const UserManagement = () => {
  const [users, setUsers] = useState([
    {
      id: 1,
      email: 'prumpy11@naver.com',
      name: 'sss',
      nickname: '망지',
      phone: '010-1595-5468',
      reports: 2,
    },
    {
      id: 2,
      email: 'example2@naver.com',
      name: '홍길동',
      nickname: '길동이',
      phone: '010-1234-5678',
      reports: 0,
    },
    // Add more users as needed
  ]);
  const [hoveredUser, setHoveredUser] = useState(null);
  const [selectedUser, setSelectedUser] = useState(null); // For dropdown selection
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  const handleSelect = (user) => {
    setSelectedUser(user);
    setHoveredUser(user);
    setIsDropdownOpen(false); // Close dropdown after selection
  };

  return (
    <div className="user-management">
      <h1 className="header">관리자</h1>
      <div className="tabs">
        <button className="tab active">회원목록</button>
        <button className="tab">문의내용</button>
      </div>

      <div className="search">
        <input type="text" placeholder="회원 이메일 검색" />
      </div>

      <div className="user-list">
        {/* Dropdown */}
        <div className="dropdown">
          <button
            className="dropdown-toggle"
            onClick={() => setIsDropdownOpen((prev) => !prev)}
          >
            {selectedUser ? selectedUser.email : '회원 선택'}
          </button>
          {isDropdownOpen && (
            <ul className="dropdown-menu">
              {users.map((user) => (
                <li
                  key={user.id}
                  onClick={() => handleSelect(user)}
                  className="dropdown-item"
                >
                  {user.email} - {user.name}
                </li>
              ))}
            </ul>
          )}
        </div>

        {hoveredUser && (
          <div className="user-details">
            <h3>사용자 상세정보</h3>
            <p>닉네임: {hoveredUser.nickname}</p>
            <p>휴대폰 번호: {hoveredUser.phone}</p>
            <p>신고된 건수: {hoveredUser.reports}</p>
            <button
              className="edit-btn"
              onClick={() => alert(`사용자 ${hoveredUser.name} 수정`)}
            >
              수정
            </button>
          </div>
        )}
      </div>

      <button className="delete-btn">회원 삭제</button>
    </div>
  );
};

export default UserManagement;
