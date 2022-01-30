import * as React from 'react';
import { useSelector } from 'react-redux';
import axios from 'axios';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import Button from '@mui/material/Button';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { MainState } from '../store';
import { fromUnixToTodayDistance } from '../util/time';

interface Task {
  createdAt: number;
  deadline: number;
  done: boolean;
  id: number;
  title: string;
}

interface Props {
  tasks: Task[];
}

const TasksTable: React.FC<Props> = ({ tasks }) => {
  const { accessToken } = useSelector((state: MainState) => state);
  const [tasksList, setTasksList] = React.useState(tasks);

  const toggleTask = async (id: number): Promise<void> => {
    console.log(accessToken);
    try {
      await axios.patch(
        `http://localhost:8080/api/v1/tasks/${id}`,
        {},
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      setTasksList(
        tasksList.map((task) =>
          task.id === id ? { ...task, done: !task.done } : task
        )
      );
    } catch {
      console.error('error');
    }
  };
  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 650 }} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell align="right">id</TableCell>
            <TableCell align="right">title</TableCell>
            <TableCell align="right">done</TableCell>
            <TableCell align="right">created at</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {tasksList.map((task) => (
            <TableRow
              key={task.id}
              sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
            >
              <TableCell align="right">{task.id}</TableCell>
              <TableCell align="right">{task.title}</TableCell>
              <TableCell align="right">
                <Button size="small" onClick={() => toggleTask(task.id)}>
                  {task.done ? <span>Done</span> : <span>Undone</span>}
                </Button>
              </TableCell>
              <TableCell align="right">
                {fromUnixToTodayDistance(task.createdAt)}
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default TasksTable;
