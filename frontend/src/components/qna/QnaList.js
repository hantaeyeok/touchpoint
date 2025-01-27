import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import "@styles/Qna.css";

function QnaList() {
    const [qnaList, setQnaList] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    const fetchQnas = async (page) => {
        try {
            const response = await axios.get("http://localhost:8989/qna/qnaList", {params: { page, size: 10 }});
            
            const data = response.data.data;
            setQnaList(data.qnaList);
            setTotalPages(data.totalPages);
            setCurrentPage(data.currentPage);
        } catch (error) {
            console.error("FAQ 데이터를 가져오는 중 오류 발생:", error);
        }
    };

    useEffect(() => {
        fetchQnas(currentPage);
    }, [currentPage]);

    const handlePageChange = (page) => {
        setCurrentPage(page);
    };

    return (
        <div>
            <div className="qnaList">
                <table>
                    <thead>
                        <tr>
                            <th>NO.</th>
                            <th>제목</th>
                            <th>작성자</th>
                            <th>등록일</th>
                            <th>상태</th>
                        </tr>
                    </thead>
                    <tbody>
                        {qnaList.map((item) => (
                            <tr key={item.qnaNo}>
                                <td>{item.qnaNo}</td>
                                <td><Link to={`/qnaDetail/${item.qnaNo}`}>{item.qnaTitle}</Link></td>
                                <td>{item.userId}</td>
                                <td>{item.qnaDate}</td>
                                <td className="status question">{item.status}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            <div className="pagination">
                {Array.from({ length: totalPages }, (_, i) => (
                    <button
                        key={i}
                        className={i === currentPage ? "active" : ""}
                        onClick={() => handlePageChange(i)}>
                        {i + 1}
                    </button>
                ))}
            </div>
        </div>
    );
}

export default QnaList;
