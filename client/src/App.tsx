import React from 'react';
import { Provider } from 'react-redux';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import MainLayout from './layouts/MainLayout';
import EditTaskListPage from './pages/EditTaskListPage';
import HomePage from './pages/Home';
import NewTaskPage from './pages/NewTask';
import NewTaskListPage from './pages/NewTaskList';
import NotFoundPage from './pages/NotFound';
import RegisterPage from './pages/Register';
import TaskListPage from './pages/TaskListPage';
import TaskListsPage from './pages/TaskListsPage';
import PrivateRoute from './security/PrivateRoute';
import { store } from './store';

const App: React.FC = () => (
  <Provider store={store}>
    <MainLayout>
      <BrowserRouter>
        <Switch>
          <Route exact path="/" component={HomePage} />
          <Route path="/register" component={RegisterPage} />
          <PrivateRoute
            path="/task-lists/:id/edit"
            component={EditTaskListPage}
          />
          <PrivateRoute path="/task-lists/:id" component={TaskListPage} />
          <PrivateRoute path="/task-lists" component={TaskListsPage} />
          <PrivateRoute path="/new-task-list" component={NewTaskListPage} />
          <PrivateRoute path="/list/:id/new-task" component={NewTaskPage} />
          <Route path="*" component={NotFoundPage} />
        </Switch>
      </BrowserRouter>
    </MainLayout>
  </Provider>
);

export default App;
