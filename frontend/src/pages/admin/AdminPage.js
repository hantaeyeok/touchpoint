import React, { useState } from "react";
// import UserManagement from "../components/UserManagement";
// import AdConsentManagement from "../components/AdConsentManagement";
// import Statistics from "../components/Statistics";
// import EmailNotification from "../components/EmailNotification";
// import "./AdminPage.css";

const AdminPage = () => {
  const [activeTab, setActiveTab] = useState("userManagement");

//   const renderTabContent = () => {
//     switch (activeTab) {
//       case "userManagement":
//         return <UserManagement />;
//       case "adConsentManagement":
//         return <AdConsentManagement />;
//       case "statistics":
//         return <Statistics />;
//       case "emailNotification":
//         return <EmailNotification />;
//       default:
//         return null;
//     }
//   };

  return (
    <div className="admin-page">
      <header className="admin-header">
        <h1>TouchPoint 관리자 페이지</h1>
        <nav className="admin-nav">
          <ul>
            <li
              className={activeTab === "userManagement" ? "active" : ""}
              onClick={() => setActiveTab("userManagement")}
            >
              회원목록
            </li>
            <li
              className={activeTab === "adConsentManagement" ? "active" : ""}
              onClick={() => setActiveTab("adConsentManagement")}
            >
              광고 동의 관리
            </li>
            <li
              className={activeTab === "statistics" ? "active" : ""}
              onClick={() => setActiveTab("statistics")}
            >
              통계 및 보고
            </li>
            <li
              className={activeTab === "emailNotification" ? "active" : ""}
              onClick={() => setActiveTab("emailNotification")}
            >
              이메일 및 알림 관리
            </li>
          </ul>
        </nav>
      </header>
      {/* <main className="admin-content">{renderTabContent()}</main> */}
    </div>
  );
};

export default AdminPage;
