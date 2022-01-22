import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import { CircularProgress, Typography } from '@mui/material';
import { MainState } from '../store';
import { GET_TASK_LIST_URL } from '../constants/urls';
import { fromUnixToTodayDistance } from '../util/time';

interface Params {
  id: string;
}

interface TaskList {
  createdAt: number;
  done: number;
  id: number;
  title: string;
  total: number;
  undone: number;
}

const TaskListPage: React.FC = () => {
  const [taskList, setTaskList] = useState<TaskList>({
    createdAt: 1642882196.760495,
    done: 0,
    id: 5,
    title: 'string 4',
    total: 0,
    undone: 0,
  });
  const [loading, setLoading] = useState(false);
  const [tasks, setTasks] = useState([]);
  const { id } = useParams<Params>();
  const { accessToken } = useSelector((state: MainState) => state);

  useEffect(() => {
    const fetchTaskListDetails = async (): Promise<void> => {
      if (id.length > 0) {
        setLoading(true);
        try {
          const { data: listDetails } = await axios.get(
            `${GET_TASK_LIST_URL}${id}`,
            {
              headers: {
                Authorization: `Bearer ${accessToken}`,
              },
            }
          );
          setTaskList(listDetails);
          console.log(listDetails);

          const { data: tasksForCurrentTaskList } = await axios.get(
            `http://localhost:8080/api/v1/task-lists/${id}/tasks`,
            {
              headers: {
                Authorization: `Bearer ${accessToken}`,
              },
            }
          );
          console.log(tasksForCurrentTaskList);
          setTasks(tasksForCurrentTaskList);
          setLoading(false);
        } catch {
          console.error('an error occurred');
          setLoading(false);
        }
      }
    };
    fetchTaskListDetails();

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  if (loading) {
    return <CircularProgress />;
  }

  return (
    <>
      <Typography variant="h4" component="h1" gutterBottom>
        {taskList?.title}
      </Typography>
      <Typography variant="h5" component="h2" gutterBottom>
        Total: {taskList?.total} ,Done: {taskList?.done}, Undone:
        {taskList?.undone}
      </Typography>
      <Typography variant="h6" component="h4" gutterBottom>
        {fromUnixToTodayDistance(taskList.createdAt)}
      </Typography>
    </>
  );
};

export default TaskListPage;
