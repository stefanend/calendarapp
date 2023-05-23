import {
  FormControl,
  InputLabel,
  MenuItem,
  Popover,
  Select,
  Stack,
  TextField,
  Typography,
} from '@mui/material';
import Button from '@mui/material/Button';
import './styles/AppointmentForm.css';
import BasicDatePicker from './DatePicker';
import { useState } from 'react';
import moment from 'moment/moment';
import dayjs from 'dayjs';

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
  PaperProps: {
    style: {
      maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
      width: 250,
    },
  },
};

const getClosestTimeFromNow = () => {
  const now = new Date();
  if (now.getHours() >= 16) return '09:00';
  if (now.getMinutes() >= 30) return now.getHours() + 1 + ':00';
  return now.getHours() + ':30';
};

const getDefaultDate = () => {
  const now = dayjs(new Date());
  if (now.hour() >= 16) return now.add(1, 'day');
  return now;
};

export default function AppointmentForm({
  id,
  anchorEl,
  open,
  handleClose,
  triggerFetch,
  openAlert,
}) {
  const [time, setTime] = useState(getClosestTimeFromNow());
  const [date, setDate] = useState(getDefaultDate());
  const [candidateFieldValue, setCandidateFieldValue] = useState('');
  const [expInterviewerFieldValue, setExpInterviewerFieldValue] = useState('');
  const [inexpInterviewerFieldValue, setInexpInterviewerFieldValue] =
    useState('');

  const getTimeValues = () => {
    const hours = [];

    for (let hour = 9; hour < 17; hour++) {
      hours.push(moment({ hour }).format('HH:mm'));
      hours.push(
        moment({
          hour,
          minute: 30,
        }).format('HH:mm')
      );
    }
    hours.pop();
    return hours;
  };

  const splitName = (fullName) => {
    const words = fullName.split(' ');
    const firstName = words[0];
    const lastName = words[1] || '';
    return [firstName, lastName];
  };

  const resetFields = () => {
    setCandidateFieldValue('');
    setExpInterviewerFieldValue('');
    setInexpInterviewerFieldValue('');
  };

  const validateForm = (e) => {
    e.preventDefault();
    if (!date || !time) {
      openAlert('Please fill in the required fields.', 'error');
      return;
    }
    if (date.day() === 0 || date.day() === 6) {
      openAlert('Interview cannot be scheduled on the weekend.', 'error');
      return;
    }

    let dateAndTime = new Date(date.toDate());
    dateAndTime.setUTCHours(Number.parseInt(time.slice(0, 2)));
    dateAndTime.setUTCMinutes(Number.parseInt(time.slice(3)));

    let candidate = null;
    let experiencedInterviewer = null;
    let inexperiencedInterviewer = null;

    if (candidateFieldValue !== '') {
      const [candidateName, candidateLastName] = splitName(candidateFieldValue);
      candidate = {
        firstName: candidateName,
        lastName: candidateLastName,
      };
    }

    if (expInterviewerFieldValue !== '') {
      const [expInterviewerName, expInterviewerLastName] = splitName(
        expInterviewerFieldValue
      );
      experiencedInterviewer = {
        firstName: expInterviewerName,
        lastName: expInterviewerLastName,
        experienced: true,
      };
    }

    if (inexpInterviewerFieldValue !== '') {
      const [inexpInterviewerName, inexpInterviewerLastName] = splitName(
        inexpInterviewerFieldValue
      );
      inexperiencedInterviewer = {
        firstName: inexpInterviewerName,
        lastName: inexpInterviewerLastName,
        experienced: false,
      };
    }

    addAppointment(
      dateAndTime.toISOString(),
      candidate,
      experiencedInterviewer,
      inexperiencedInterviewer
    );
  };

  const addAppointment = (
    day,
    candidate,
    experiencedInterviewer,
    inexperiencedInterviewer
  ) => {
    let appointment = {};
    appointment.day = day;
    appointment.interviewers = [];
    if (candidate) appointment.candidate = candidate;
    if (experiencedInterviewer)
      appointment.interviewers.push(experiencedInterviewer);
    if (inexperiencedInterviewer)
      appointment.interviewers.push(inexperiencedInterviewer);

    fetch(`http://localhost:8080/api/appointments`, {
      method: 'POST',
      headers: {
        'Accept': 'application/json, text/plain',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(appointment),
    })
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        openAlert('Appointment was successfully added!', 'success');
        triggerFetch(date);
        resetFields();
      })
      .catch((error) => {
        openAlert('An error occured while adding appointment.', 'error');
      });
  };

  return (
    <Popover
      id={id}
      open={open}
      anchorEl={anchorEl}
      onClose={handleClose}
      anchorOrigin={{
        vertical: 'bottom',
        horizontal: 'left',
      }}
      transformOrigin={{
        vertical: 'top',
        horizontal: 'center',
      }}
    >
      <form onSubmit={(e) => validateForm(e)}>
        <Stack direction='column' className='form-wrapper'>
          <Typography variant='h6'>Schedule new appointment</Typography>
          <FormControl fullWidth>
            <BasicDatePicker
              value={date}
              onDateChange={(newValue) => setDate(newValue)}
            />
          </FormControl>
          <FormControl>
            <FormControl sx={{ marginTop: '15px', width: '50%' }}>
              <InputLabel id='demo-simple-select-label'>Time</InputLabel>
              <Select
                id='demo-simple-select-label'
                label='Time'
                onChange={(e) => setTime(e.target.value)}
                MenuProps={MenuProps}
                placeholder={getClosestTimeFromNow()}
                value={time}
              >
                {getTimeValues().map((time, index) => (
                  <MenuItem value={time} key={index}>
                    {time}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
            <TextField
              id='outlined-search'
              placeholder='Candidate Name'
              type='search'
              sx={{
                '& fieldset': { border: 'none' },
                marginTop: '10px',
              }}
              inputProps={{
                style: { fontSize: '11pt', padding: '8px' },
              }}
              onChange={(e) => setCandidateFieldValue(e.target.value)}
            />
          </FormControl>
          <FormControl>
            <TextField
              id='outlined-search'
              placeholder='Experienced Interviewer Name'
              type='search'
              sx={{
                '& fieldset': { border: 'none' },
                marginTop: '10px',
              }}
              inputProps={{
                style: { fontSize: '11pt', padding: '8px' },
              }}
              onChange={(e) => setExpInterviewerFieldValue(e.target.value)}
            />
          </FormControl>
          <FormControl>
            <TextField
              id='outlined-search'
              placeholder='Inexperienced Interviewer Name'
              type='search'
              sx={{
                '& fieldset': { border: 'none' },
                marginTop: '10px',
              }}
              inputProps={{
                style: { fontSize: '11pt', padding: '8px' },
              }}
              onChange={(e) => setInexpInterviewerFieldValue(e.target.value)}
            />
          </FormControl>
          <Stack direction='row' alignSelf='flex-end'>
            <Button autoFocus onClick={handleClose}>
              Close
            </Button>
            <Button autoFocus type='submit'>
              Submit
            </Button>
          </Stack>
        </Stack>
      </form>
    </Popover>
  );
}
