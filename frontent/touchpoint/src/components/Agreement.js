import React, { useState } from "react";
import "../styles/Agreement.css";

const Agreement = ({ agreements, onChange }) => {
  const [showModal, setShowModal] = useState(null); // 모달 상태

  // 약관 모달 열기
  const openModal = (type) => {
    setShowModal(type);
  };

  // 약관 모달 닫기
  const closeModal = () => {
    setShowModal(null);
  };

  return (
    <div className="agreements-section">
      {/* 필수 약관 */}
      <label className="agreement-item">
        <input
          type="checkbox"
          checked={agreements.required}
          onChange={() => onChange("required")}
        />
        <span onClick={() => openModal("required")} className="agreement-link">
          (필수) 개인정보 처리방침 동의
        </span>
      </label>

      {/* 선택 약관 */}
      <label className="agreement-item">
        <input
          type="checkbox"
          checked={agreements.optional}
          onChange={() => onChange("optional")}
        />
        <span onClick={() => openModal("optional")} className="agreement-link">
          (선택) 광고 수신 동의
        </span>
      </label>

      {/* 모달 창 */}
      {showModal && (
        <div className="modal">
          <div className="modal-content">
            <h3>{showModal === "required" ? "개인정보 처리방침" : "광고 수신 동의"}</h3>
            <p>
              {showModal === "required"
                ? "여기에 개인정보 처리방침 내용을 작성하세요."
                : "여기에 광고 수신 동의 내용을 작성하세요."}
            </p>
            <button onClick={closeModal} className="modal-close-button">
              닫기
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default Agreement;
