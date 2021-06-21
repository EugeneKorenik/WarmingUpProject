const backendUrl = "http://localhost:8080/api/v1/";

export const pictures = backendUrl + "pictures";
export const figures = backendUrl + "figures";

export const applicationJsonContentType = {
    'Content-Type': 'application/json'
};

export const createPicture = (pictureName) => {
    const options = {
        method: 'post',
        body: JSON.stringify({
            name: pictureName
        }),
        headers: applicationJsonContentType
    };

    return fetch(pictures, options);
}

export const updatePicture = (id, properties) => {
    const url = `${pictures}/${id}`;
    const options = {
        method: 'put',
        headers: applicationJsonContentType,
        body: JSON.stringify({
            ...properties
        })
    };

    return fetch(url, options);
}

export const deletePicture = (pictureId) => {
    const uri = `${pictures}/${pictureId}`;
    const options = {
        method: 'delete',
        headers: applicationJsonContentType
    }

    return fetch(uri, options);
}

export function updateFigure(id, figureType, properties) {
    if (id) {
        const typeUppercase = figureType.toUpperCase();
        const url = `${figures}/${typeUppercase}/${id}`;
        const options = {
            method: 'put',
            headers: applicationJsonContentType,
            body: JSON.stringify({
                type: figureType,
                ...properties
            })
        }
        return fetch(url, options);
    }
}


export function deleteFigure(id, type) {
    type = type.toUpperCase();
    const url = `${figures}/${type}/${id}`;
    const options = {
        method: 'delete',
        headers: applicationJsonContentType
    };

    return fetch(url, options);
}