import { useEffect, useState } from 'react';
import { experimentalStyled as styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';

const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
  ...theme.typography.body2,
  padding: theme.spacing(2),
  textAlign: 'center',
  color: theme.palette.text.secondary,
}));

export default function ResponsiveGrid() {
  const [appointments, setAppointments] = useState([]);
  
  const daysOfWeek = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'];
  
  const getAppointmentsForWeek = () => {
    let currentDate = new Date();
    let startDate = new Date();
    startDate.setDate(currentDate.getDate() - startDate.getDay() + 1);
    let endDate = new Date();
    endDate.setDate(startDate.getDate() + 6);
  
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

  const groupAppointmentsByDay = (rawAppointments) => {
    const groupedAppointments = rawAppointments.reduce((accumulator, currentValue) => {
      let currentDate = new Date(currentValue.day);
      if(!accumulator[currentDate.getDay() - 1]) {
        accumulator[currentDate.getDay() - 1] = [];
      }
      accumulator[currentDate.getDay() - 1].push(currentValue); 
      return accumulator;
    }, Array(5));
    return groupedAppointments;
  };

  useEffect(() => {
    getAppointmentsForWeek();    
  },[]);

  return (
    <Box sx={{ flexGrow: 1 }}>
        <Grid container spacing={{ xs: 2, md: 2 }} columns={{ xs: 2, sm: 2, md: 1 }}>
        {Array.from(Array(1)).map((_, index) => (
          <Grid item xs={2} sm={4} md={4} key={index}>
            <Item>Self scheduling calendar - weekly</Item>
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

        {appointments?.map((appointmentDay) => {
          return (

          <Grid item xs={2} sm={4} md={4}>
            
            { appointmentDay.map(appointment => {
              return(  
              <Box key={appointment.id}>
                <Item>{ appointment.id }</Item>
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
