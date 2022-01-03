import { CircularProgress, Typography } from '@mui/material';
import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
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
    return <p>No data to show....</p>;
  }

  return (
    <>
      <Typography variant="h4" component="h1" gutterBottom>
        Your task lists.
      </Typography>
      <TaskListsTable
        taskLists={taskLists}
        deleteTaskListById={deleteTaskListById}
      />
    </>
  );
};

export default TaskListsPage;
