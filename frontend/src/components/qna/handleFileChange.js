

export const handleFileChange = (event) => {
    const fileList = Array.from(event.target.files);
    return fileList.map((file) => file.name).join(", ");
};