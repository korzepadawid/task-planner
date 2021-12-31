import React, { useState } from 'react';
import Typography from '@mui/material/Typography';
import { LoadingButton } from '@mui/lab';
import {
  Alert,
  Button,
  Card,
  CardContent,
  Grid,
  TextField,
} from '@mui/material';
import axios from 'axios';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { useDispatch } from 'react-redux';
import { LOGIN_URL } from '../constants/urls';
import { login } from '../store/actionCreators';

interface FormInput {
  email: string;
  password: string;
}

const formSchema = yup
  .object({
    email: yup.string().email().trim().required(),
    password: yup.string().trim().required(),
  })
  .required();

const LoginCard: React.FC = () => {
  const dispatch = useDispatch();
  const [error, setError] = useState(false);
  const [loading, setLoading] = useState(false);

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<FormInput>({
    resolver: yupResolver(formSchema),
  });

  const onSubmit = async (input: FormInput): Promise<void> => {
    setLoading(true);
    try {
      const { data } = await axios.post(LOGIN_URL, {
        ...input,
      });
      const { accessToken } = data;
      setError(false);
      dispatch(login(accessToken));
    } catch {
      setError(true);
    }
    setLoading(false);
  };

  return (
    <Card component="form" onSubmit={handleSubmit(onSubmit)}>
      <CardContent>
        <Grid container spacing={3} direction="column" alignItems="center">
          <Grid item xs={12}>
            <Typography variant="h6" component="h3" gutterBottom>
              Login
            </Typography>
          </Grid>
          {error && (
            <Grid item xs={12}>
              <Alert severity="error">
                Ooops, invalid credentials. Try again, please!
              </Alert>
            </Grid>
          )}
          <Grid item xs={12}>
            <TextField
              error={errors.email !== undefined}
              {...register('email')}
              helperText={errors.email?.message}
              fullWidth
              id="outlined-basic"
              label="Email"
              variant="outlined"
              size="small"
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              error={errors.password !== undefined}
              {...register('password')}
              helperText={errors.password?.message}
              fullWidth
              id="outlined-basic"
              label="Password"
              type="password"
              variant="outlined"
              size="small"
            />
          </Grid>
          <Grid
            item
            container
            spacing={3}
            direction="row"
            justifyContent="center"
            alignItems="center"
          >
            <Grid item>
              <LoadingButton
                type="submit"
                component="button"
                variant="contained"
                loading={loading}
              >
                Login
              </LoadingButton>
            </Grid>
            <Grid item>
              <Button href="#text-buttons">Register</Button>
            </Grid>
          </Grid>
        </Grid>
      </CardContent>
    </Card>
  );
};

export default LoginCard;
