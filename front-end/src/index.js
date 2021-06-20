import React from 'react';
import ReactDOM from 'react-dom';
import PicturesList from './app/picturesList/PicturesList';
import Picture from './app/picture/Picture';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import { DndProvider } from 'react-dnd';
import { HTML5Backend } from 'react-dnd-html5-backend';

ReactDOM.render(
  <React.StrictMode>
    <DndProvider backend={HTML5Backend}>
      <BrowserRouter>
        <Switch>
          <Route path="/pictures/:pictureId" component={Picture} />
          <Route path="/" exact component={PicturesList} />
        </Switch>
      </BrowserRouter>
    </DndProvider>
  </React.StrictMode>,
  document.getElementById('root')
);