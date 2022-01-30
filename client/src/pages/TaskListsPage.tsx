import { CircularProgress, Typography } from '@mui/material';
import axios from 'axios';
import Button from '@mui/material/Button';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import TaskListsTable from '../components/TaskListsTable';
import { DELETE_TASK_LIST_URL, GET_TASK_LISTS_URL } from '../constants/urls';
import { MainState } from '../store';

export interface TaskList {
  id: number;
  title: string;
  createdAt: number;
  undone: number;
  done: number;
  total: number;
}

const TaskListsPage: React.FC = () => {
  const [taskLists, setTaskLists] = useState([]);
  const [loading, setLoading] = useState(false);
  const { accessToken } = useSelector((state: MainState) => state);

  const deleteTaskListById = async (id: number): Promise<void> => {
    setTaskLists(taskLists.filter((taskList: TaskList) => taskList.id !== id));
    await axios.delete(`${DELETE_TASK_LIST_URL}${id}`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    });
  };

  useEffect(() => {
    const fetchTaskGroups = async (): Promise<void> => {
      setLoading(true);
      const { data } = await axios.get(GET_TASK_LISTS_URL, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });
      setTaskLists(data);
      setLoading(false);
    };
    fetchTaskGroups();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [accessToken]);

  if (loading) {
    return <CircularProgress />;
  }

  if (taskLists.length === 0) {
    return (
      <>
        No data to show....
        <Button href="#text-buttons">
          <Link to="/new-task-list">NEW LIST</Link>
        </Button>
      </>
    );
  }

  return (
    <>
      <Typography variant="h4" component="h1" gutterBottom>
        Your task lists.
      </Typography>
      <Button href="#text-buttons">
        <Link to="/new-task-list">NEW LIST</Link>
      </Button>
      <TaskListsTable
        taskLists={taskLists}
        deleteTaskListById={deleteTaskListById}
      />
    </>
  );
};

export default TaskListsPage;
