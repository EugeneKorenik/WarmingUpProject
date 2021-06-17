import React from 'react';
import ReactDOM from 'react-dom';
import Application from './Application';
import { DndProvider } from 'react-dnd';
import { HTML5Backend } from 'react-dnd-html5-backend';

ReactDOM.render(
  <React.StrictMode>
    <DndProvider backend={HTML5Backend}>
      <Application />
    </DndProvider>
  </React.StrictMode>,
  document.getElementById('root')
);