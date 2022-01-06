import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useSelector } from 'react-redux';
import { useParams, useLocation } from 'react-router-dom';
import { MainState } from '../store';
import { GET_TASK_LIST_URL } from '../constants/urls';

interface Params {
  id: string;
}

const TaskListPage: React.FC = () => {
  const [taskList, setTaskList] = useState();
  const [tasks, setTasks] = useState([]);
  const { search } = useLocation();
  const page = new URLSearchParams(search).get('page');
  const { id } = useParams<Params>();
  const { accessToken } = useSelector((state: MainState) => state);
  useEffect(() => {
    const fetchTaskListDetails = async (): Promise<void> => {
      if (id.length > 0) {
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
            `http://localhost:8080/api/v1/task-lists/${id}/tasks?page=${page}`,
            {
              headers: {
                Authorization: `Bearer ${accessToken}`,
              },
            }
          );
          console.log(tasksForCurrentTaskList);
          setTasks(tasksForCurrentTaskList);
        } catch {
          console.error('an error occurred');
        }
      }
    };
    fetchTaskListDetails();

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);
  return (
    <>
      <h1>Task list page {parseInt(id, 10)}</h1>
    </>
  );
};

export default TaskListPage;
