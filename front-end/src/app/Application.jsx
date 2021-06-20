import React from 'react';
import Picture from './picture/Picture';
import PictureList from './picturesList/PicturesList';
import './application.css';

class Application extends React.Component {

    render() {
        const picture = <Picture />;
        const pictureList = <PictureList />;
        return (
            <div id="application">
                {pictureList}
            </div>
        );
    }
}

export default Application;