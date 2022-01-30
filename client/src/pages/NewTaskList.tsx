import React from 'react';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { useHistory } from 'react-router-dom';
import * as yup from 'yup';
import axios from 'axios';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import { Card, Grid, Typography } from '@mui/material';
import { useSelector } from 'react-redux';
import { MainState } from '../store';

interface FormInput {
  title: string;
}

const formSchema = yup
  .object({
    title: yup.string().trim().required(),
  })
  .required();

const NewTaskListPage: React.FC = () => {
  const history = useHistory();
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<FormInput>({
    resolver: yupResolver(formSchema),
  });
  const { accessToken } = useSelector((state: MainState) => state);

  const submitForm = async (input: FormInput): Promise<void> => {
    try {
      await axios.post(
        'http://localhost:8080/api/v1/task-lists',
        {
          ...input,
        },
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      history.push('/task-lists');
      console.log('test');
    } catch {
      console.error('error');
    }
  };

  return (
    <>
      <Typography variant="h6" component="h3" gutterBottom>
        New task list
      </Typography>
      <form onSubmit={handleSubmit(submitForm)}>
        <Grid direction="column" container spacing={3}>
          <Grid item xs={12}>
            <TextField
              {...register('title')}
              id="outlined-basic"
              label="Title"
              variant="outlined"
            />
          </Grid>
          <Grid item xs={12}>
            <Button type="submit" variant="contained">
              Submit
            </Button>
          </Grid>
        </Grid>
      </form>
    </>
  );
};

export default NewTaskListPage;
