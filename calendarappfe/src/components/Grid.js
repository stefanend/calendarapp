import * as React from 'react';
import { useEffect, useState } from 'react';
import Appointment from './Appointment';
import Calendar from './Calendar';
import { experimentalStyled as styled } from '@mui/material/styles';
import { Box } from '@mui/material';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import dayjs from 'dayjs';
import Button from '@mui/material/Button';
import AddIcon from '@mui/icons-material/Add';
import AppointmentForm from './AppointmentForm';

const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
  ...theme.typography.body2,
  padding: theme.spacing(2),
  textAlign: 'center',
  color: theme.palette.text.secondary,
}));

export default function ResponsiveGrid({ openAlert }) {
  const [appointments, setAppointments] = useState([]);
  const [anchorEl, setAnchorEl] = useState(null);
  const [formAnchorEl, setFormAnchorEl] = useState(null);
  const [weekStart, setWeekStart] = useState(dayjs(Date.now()));
  const [weekEnd, setWeekEnd] = useState(dayjs(Date.now()));

  const handleAddAppointmentOpen = (event) => {
    setFormAnchorEl(event.currentTarget);
    console.log(formAnchorEl);
  };

  const handleAddAppointmentClose = () => {
    setFormAnchorEl(null);
  };

  const handlePopoverClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleCalendarPopoverClose = () => {
    setAnchorEl(null);
  };

  const calendarPopoverOpen = Boolean(anchorEl);
  const addAppointmentFormOpen = Boolean(formAnchorEl);
  const popoverId = calendarPopoverOpen ? 'simple-popover' : undefined;
  const formPopoverId = addAppointmentFormOpen ? 'simple-popover' : undefined;

  const daysOfWeek = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'];

  const getAppointmentsForWeek = (selectedDay) => {
    const startDate = selectedDay.startOf('week');
    const endDate = selectedDay.endOf('week');
    setWeekStart(startDate);
    setWeekEnd(endDate);

    console.log(selectedDay.startOf('week').toDate());
    console.log(selectedDay.endOf('week').toDate());

    fetch(
      `http://localhost:8080/api/appointments/date?startDate=${startDate.toISOString()}&endDate=${endDate.toISOString()}`,
      { method: 'GET' }
    )
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        return groupAppointmentsByDay(data);
      })
      .then((groupedAppointments) => {
        setAppointments(groupedAppointments);
      });
  };

  const groupAppointmentsByDay = (rawAppointments) => {
    // one list for each weekday
    let startArray = Array.of([], [], [], [], []);
    const groupedAppointments = rawAppointments.reduce(
      (accumulator, appointment) => {
        let currentDate = new Date(appointment.day);
        accumulator[currentDate.getDay() - 1].push(appointment);
        return accumulator;
      },
      startArray
    );
    return groupedAppointments;
  };

  const groupAppointmentsByHour = (appointmentDay) => {
    const groupedAppointments = appointmentDay.reduce(
      (accumulator, appointment) => {
        let currentDate = new Date(appointment.day);
        let currentTime = currentDate.toISOString().substring(11, 16);
        if (!accumulator[currentTime]) {
          accumulator[currentTime] = [];
        }

        accumulator[currentTime].push(appointment);
        return accumulator;
      },
      {}
    );
    return groupedAppointments;
  };

  useEffect(() => {
    getAppointmentsForWeek(dayjs(Date.now()));
  }, []);

  return (
    <Box sx={{ flexGrow: 1 }}>
      <Grid
        container
        spacing={{ xs: 2, md: 2 }}
        columns={{ xs: 2, sm: 2, md: 1 }}
      >
        {Array.from(Array(1)).map((_, index) => (
          <Grid item xs={2} sm={4} md={4} key={index}>
            <Item
              sx={{
                background: '#005693',
                color: 'white',
                fontSize: '20pt',
                fontWeight: 'bold',
              }}
            >
              <div style={{ marginBottom: '5px' }}>
                Self scheduling calendar - weekly
              </div>
            </Item>
          </Grid>
        ))}
      </Grid>
      <Box
        sx={{
          display: 'flex',
          justifyContent: 'space-between',
          alignItems: 'center',
          marginTop: '10px',
          marginBottom: '10px',
        }}
      >
        <Button
          aria-describedby={popoverId}
          variant='contained'
          onClick={handlePopoverClick}
          sx={{ margin: '0 auto' }}
        >
          {weekStart.toDate().toLocaleDateString()} -{' '}
          {weekEnd.toDate().toLocaleDateString()}
        </Button>
        <Button
          aria-describedby={formPopoverId}
          variant='contained'
          onClick={handleAddAppointmentOpen}
        >
          <AddIcon sx={{ fontSize: 'medium', marginRight: '5px' }} />
          Create
        </Button>
        <AppointmentForm
          id={formPopoverId}
          anchorEl={formAnchorEl}
          open={addAppointmentFormOpen}
          handleClose={handleAddAppointmentClose}
          triggerFetch={getAppointmentsForWeek}
          openAlert={openAlert}
        />

        <Calendar
          id={popoverId}
          open={calendarPopoverOpen}
          anchorEl={anchorEl}
          getAppointmentsForWeek={getAppointmentsForWeek}
          handleClose={handleCalendarPopoverClose}
        />
      </Box>
      <Grid
        container
        spacing={{ xs: 2, md: 2 }}
        columns={{ xs: 2, sm: 2, md: 20 }}
      >
        {Array.from(Array(5)).map((_, index) => (
          <Grid item xs={2} sm={4} md={4} key={index}>
            <Item
              sx={{
                background: '#1E90FF',
                color: 'white',
                fontWeight: 'bold',
              }}
            >
              {daysOfWeek[index]}
            </Item>
          </Grid>
        ))}

        {appointments?.map((appointmentDay, index) => {
          return (
            <Grid item xs={2} sm={4} md={4} key={index}>
              {Object.entries(groupAppointmentsByHour(appointmentDay)).map(
                (appointmentHours, index) => {
                  return (
                    <Box key={index}>
                      <Item
                        style={{
                          backgroundColor: '#E8EBF0',
                        }}
                      >
                        <div style={{ marginTop: '-10px' }}>
                          {' '}
                          {appointmentHours[0]}{' '}
                        </div>
                        {appointmentHours[1].map((appointment, index) => {
                          return (
                            <div key={index}>
                              {index === 0 ? (
                                <Appointment
                                  appointment={appointment}
                                  openAlert={openAlert}
                                  triggerFetch={getAppointmentsForWeek}
                                />
                              ) : (
                                <div
                                  style={{
                                    marginTop: '10px',
                                  }}
                                >
                                  <Appointment
                                    appointment={appointment}
                                    openAlert={openAlert}
                                    triggerFetch={getAppointmentsForWeek}
                                  />
                                </div>
                              )}
                            </div>
                          );
                        })}
                      </Item>
                      <br />
                    </Box>
                  );
                }
              )}
            </Grid>
          );
        })}
      </Grid>
      <br></br>
      {/* <Grid container spacing={{ xs: 2, md: 2 }} columns={{ xs: 2, sm: 2, md: 20 }}>
        {Array.from(Array(20)).map((_, index) => (
          <Grid item xs={2} sm={4} md={4} key={index}>
            <Item>xs=2</Item>
          </Grid>
        ))}
      </Grid> */}
    </Box>
  );
}
