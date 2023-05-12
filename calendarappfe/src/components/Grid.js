import { useEffect, useState } from 'react';
import { experimentalStyled as styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import Appointment from './Appointment';
import Calendar from './Calendar';
import dayjs from 'dayjs';
import Button from '@mui/material/Button';

const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
  ...theme.typography.body2,
  padding: theme.spacing(2),
  textAlign: 'center',
  color: theme.palette.text.secondary,
}));

export default function ResponsiveGrid() {
  const [appointments, setAppointments] = useState([]);
  const [anchorEl, setAnchorEl] = useState(null);
  const [weekStart, setWeekStart] = useState(dayjs(Date.now()));
  const [weekEnd, setWeekEnd] = useState(dayjs(Date.now()));

  const handlePopoverClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleCalendarPopoverClose = () => {
    setAnchorEl(null);
  };

  const calendarPopoverOpen = Boolean(anchorEl);
  const popoverId = calendarPopoverOpen ? 'simple-popover' : undefined;
  
  const daysOfWeek = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'];
  
  const getAppointmentsForWeek = (selectedDay) => {
    const startDate = selectedDay.startOf('week');
    const endDate = selectedDay.endOf('week');
    setWeekStart(startDate);
    setWeekEnd(endDate);
  
    console.log(selectedDay.startOf('week').toDate());
    console.log(selectedDay.endOf('week').toDate());

    fetch(`http://localhost:8080/api/appointments/date?startDate=${startDate.toISOString()}&endDate=${endDate.toISOString()}`, { method: 'GET' })
    .then(response => {
      return response.json();
    })
    .then(data => {
      return groupAppointmentsByDay(data);
    })
    .then(groupedAppointments => {
      setAppointments(groupedAppointments);
    })
  };

  const formatTime = (date) => {
    return date.toISOString().substring(11, 16);
  };

  const groupAppointmentsByDay = (rawAppointments) => {
    // one list for each weekday
    let startArray = Array.of([], [], [], [], []);
    const groupedAppointments = rawAppointments.reduce((accumulator, currentValue) => {
      let currentDate = new Date(currentValue.day);
      accumulator[currentDate.getDay() - 1].push(currentValue); 
      return accumulator;
    }, startArray);
    return groupedAppointments;
  };

  useEffect(() => {
    getAppointmentsForWeek(dayjs(Date.now()));    
  },[]);

  return (
    <Box sx={{ flexGrow: 1 }}>
        <Grid container spacing={{ xs: 2, md: 2 }} columns={{ xs: 2, sm: 2, md: 1 }}>
        {Array.from(Array(1)).map((_, index) => (
          <Grid item xs={2} sm={4} md={4} key={index}>
            <Item>
              <div>Self scheduling calendar - weekly</div>
              <br/>
            <Button aria-describedby={popoverId} variant='contained' onClick={handlePopoverClick}>
              { weekStart.toDate().toLocaleDateString() } - { weekEnd.toDate().toLocaleDateString() }
            </Button>
            <Calendar 
              id={popoverId} 
              open={calendarPopoverOpen} 
              anchorEl={anchorEl} 
              getAppointmentsForWeek={getAppointmentsForWeek}
              handleClose={handleCalendarPopoverClose} 
            />
            </Item>
          </Grid>
        ))}
      </Grid>
      <br/>
      <Grid container spacing={{ xs: 2, md: 2 }} columns={{ xs: 2, sm: 2, md: 20 }}>
        {Array.from(Array(5)).map((_, index) => (
          <Grid item xs={2} sm={4} md={4} key={index}>
            <Item>{ daysOfWeek[index] }</Item>
          </Grid>

        ))}

        {appointments?.map((appointmentDay, index) => {
          return (

          <Grid item xs={2} sm={4} md={4} key={index}>
            
            { appointmentDay.map(appointment => {
              return(  
              <Box key={appointment.id}>
                <Item>
                  <span> { formatTime(new Date(appointment.day)) } </span>
                  <Appointment appointment={appointment}/>
                </Item>
                <br/>
              </Box>
              )
            })
            }
          </Grid>
          )

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
