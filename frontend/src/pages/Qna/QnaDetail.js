import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "@styles/Qna2.css";
import { Routes, Route, Link, useNavigate  } from "react-router-dom";
import QnaAnswerAdd from "@components/qna/QnaAnswerAdd";
import QnaAnswer from "@components/qna/QnaAnswer";
import axios from "axios";
import Modal from "react-modal";

Modal.setAppElement("#root");

function QnaDetail() {

    const [qnaModalIsOpen, setQnaModalIsOpen] = useState(false);
    const { qnaNo } = useParams(); // URL에서 qnaNo 가져오기
    const [hasAnswer, setHasAnswer] = useState(false);
    const navigate = useNavigate();
    const [qnaDetail, setQnaDetail] = useState({
        qnaNo: 0,
        qnaTitle: '',
        qnaContent: '',
        qnaDate: '',
        userId: '',
        phoneNo: '',
        files: []
    });

    const handleEdit = () => {
        navigate("/qnaEdit", {
            state: {
                qnaNo: qnaNo,
                qnaTitle: qnaDetail.qnaTitle,
                qnaContent: qnaDetail.qnaContent,
                qnaDate: qnaDetail.qnaDate,
                userId: qnaDetail.userId,
                phoneNo: qnaDetail.phoneNo,
                files: qnaDetail.files
            }
        });
    };

    useEffect(()=> {
        const fetchQnas = async () => {
            try {
                const response = await axios.get(`http://localhost:8989/qna/qnaDetail/${qnaNo}`);
                setQnaDetail(response.data.data);

                const answerResponse = await axios.get(`http://localhost:8989/qna/answer/${qnaNo}`);
                setHasAnswer(!!answerResponse.data.data);
            } catch (error) {
                console.error("QNA 데이터를 가져오는 중 오류 발생:", error);
            }
        };
        fetchQnas();
    },[]);

    function modalIsOpen() {
        setQnaModalIsOpen(true);
    }

    const handleDelte = async () => {
        try {
            const response = await axios.post(`http://localhost:8989/qna/qnaDelete/${qnaNo}`, qnaDetail,{ headers :{"Content-Type": "application/json"}});
            console.log("서버 응답:",response.data);
            alert("글 삭제가 정상 처리되었습니다.");
        } catch (error) {
            console.error("데이터 저장 중 오류 발생:", error.message);
            alert("글 저장 중 문제가 발생했습니다. 다시 시도해주세요.");
        };
        navigate("/qna");
        setQnaModalIsOpen(false);
    }
    
    return(
        <div className="formContainer">
            <div className="formRow">
                <div className="formField">
                    <label>작성자</label>
                    <span className="fieldValue">{qnaDetail.userId}</span>
                </div>
                <div className="formField">
                    <label>연락처</label>
                    <span className="fieldValue">{qnaDetail.phoneNo}</span>
                </div>
                <div className="formField">
                    <label>작성일</label>
                    <span className="fieldValue">{qnaDetail.qnaDate}</span>
                </div>
            </div>
            <div className="formRow">
                <div className="formField fullWidth">
                    <label>제 목</label>
                    <span className="fieldValue">{qnaDetail.qnaTitle}</span>
                </div>
            </div>
            <div className="formRow">
                <div className="formField fullWidth">
                    <label>파일첨부</label>
                    {qnaDetail.files?.length > 0 ? (
                        qnaDetail.files.map((file, index) => (
                            <a key={index} href={`http://localhost:8989/qna/download/${file.changeName}`} download={file.originName}>
                                {file.originName}
                            </a>
                        ))
                    ) : (
                        <span>첨부된 파일이 없습니다.</span>
                    )}
                </div>
            </div>
            <div className="qnaContent">
                <span className="fieldValue">{qnaDetail.qnaContent}</span>
            </div>
            <div className="qnaAdd_btn">
                <button onClick={()=>{navigate(-1)}}>이전으로</button>
                <button onClick={handleEdit}>수정하기</button>
                <button onClick={modalIsOpen}>삭제하기</button>
            </div>
            <div>
                <Modal className="deleteModal"
                    isOpen={qnaModalIsOpen}
                    onRequestClose={()=> setQnaModalIsOpen(false)}>
                    <p>삭제를 그대로 진행 하시겠습니까?</p>
                    <button onClick={handleDelte}>삭제하기</button>
                </Modal>
            </div>
            {hasAnswer ? <QnaAnswer/> : <QnaAnswerAdd/> }
        </div>
    )
}
export default QnaDetail