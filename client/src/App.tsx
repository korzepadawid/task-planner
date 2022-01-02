import React from 'react';
import { Provider } from 'react-redux';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import MainLayout from './layouts/MainLayout';
import HomePage from './pages/Home';
import NotFoundPage from './pages/NotFound';
import RegisterPage from './pages/Register';
import TaskListsPage from './pages/TaskListPage';
import PrivateRoute from './security/PrivateRoute';
import { store } from './store';

const App: React.FC = () => (
  <Provider store={store}>
    <MainLayout>
      <BrowserRouter>
        <Switch>
          <Route exact path="/" component={HomePage} />
          <Route path="/register" component={RegisterPage} />
          <PrivateRoute path="/task-groups" component={TaskListsPage} />
          <Route path="*" component={NotFoundPage} />
        </Switch>
      </BrowserRouter>
    </MainLayout>
  </Provider>
);

export default App;
