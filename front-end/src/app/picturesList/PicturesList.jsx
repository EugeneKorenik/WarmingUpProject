import React from 'react';
import * as BackendApi from '../backend-api';
import { Link } from 'react-router-dom';
import './pictureList.css';

class PicturesList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            pictures: [],
            editingPictureId: []
        };

        this.pictureNameRef = React.createRef();
        this.pictureNameEditRef = React.createRef();
    }

    componentDidMount() {
        const options = {
            headers: BackendApi.applicationJsonContentType
        }

        fetch(BackendApi.pictures, options)
            .then(response => response.json())
            .then(response => {
                this.setState({
                    pictures: response
                });
            });
    }

    createPicture = () => {
        const pictureName = this.pictureNameRef.current.value;
        if (pictureName) {
            BackendApi.createPicture(pictureName)
                .then(response => response.json())
                .then(response => {
                    const pictures = this.state.pictures;
                    pictures.push(response);
                    this.setState({
                        pictures: pictures
                    });
                });
        }
    }

    deletePicture = (pictureId) => {
        BackendApi.deletePicture(pictureId)
            .then(response => {
                const pictures = this.state.pictures.filter(nextPicture => nextPicture.id !== pictureId);
                this.setState({
                    pictures: pictures
                });
            });
    }

    startNameEditing = (picture) => {
        const pictureNameEdit = this.pictureNameEditRef.current;
        pictureNameEdit.value = picture.name;
        this.setState({
            editingPictureId: picture.id
        });
    }

    updateName(picture) {
        const newName = this.pictureNameEditRef.current.value;
        if (newName) {
            picture.name = newName;
            BackendApi.updatePicture(
                picture.id,
                {
                    name: newName
                }
            )
            .then(response => response.json())
            .then(response => {
                picture.modified = response.modified;
                this.setState({
                    editingPictureId: null,
                    pictures: this.state.pictures
                });
            });
        }
    }

    render() {
        const renderedPictures = this.state.pictures.map(picture => {
            const pictureCreated = new Date(picture.created).toString();
            const pictureModified = new Date(picture.modified).toString();

            return (<div id={picture.id} key={picture.id} className="picture">
                <div className="picture-info">
                    {
                        this.state.editingPictureId !== picture.id
                            ? <h4 onClick={() => this.setState({ editingPictureId: picture.id })}>Name: <span>{picture.name}</span> </h4>
                            : null
                    }
                    {
                        this.state.editingPictureId === picture.id
                            ? <div>
                                <input type="text"
                                    defaultValue={picture.name}
                                    ref={this.pictureNameEditRef}
                                    autoFocus
                                />
                                <button onClick={() => this.updateName(picture)}>OK</button>
                                <button onClick={() => this.setState({ editingPictureId: null })}>NO</button>
                            </div>
                            : null
                    }
                    <h4>Created: <span>{pictureCreated}</span> </h4>
                    <h4>Modified: <span>{pictureModified}</span> </h4>
                </div>
                <div className="button-bar">
                    <Link to={`/pictures/${picture.id}`}>
                        <button>Open</button>
                    </Link>
                    <button onClick={() => this.deletePicture(picture.id)}>Delete</button>
                </div>
            </div >
            )
        });

        return (
            <div className="pictures-frame">
                <div className="pictures-list">
                    <h1>Pictures: </h1>
                    <div className="scrollable">
                        {renderedPictures}
                    </div>
                </div>
                <div className="picture-form">
                    <label htmlFor="pictureNameInput"><h2>Create new picture</h2></label>
                    <input id="pictureNameInput" type="text" placeholder="picture name" ref={this.pictureNameRef} />
                    <button onClick={this.createPicture}>Create</button>
                </div>
            </div>
        );
    }

}

export default PicturesList;