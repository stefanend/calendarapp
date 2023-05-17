import { Box, FormControl, InputLabel, MenuItem, Popover, Select, Stack, TextField, Typography } from "@mui/material";
import Button from '@mui/material/Button';
import './styles/AppointmentForm.css';
import BasicDatePicker from './DatePicker';
import { useState } from "react";
import moment from "moment/moment";

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
    if(now.getHours() >= 16)
        return '09:00';
    if (now.getMinutes() >= 30)
        return now.getHours() + 1 + ':00';
    return now.getHours() + ':30'; 
};

export default function AppointmentForm({ id, anchorEl, open, handleClose }) {
    const [time, setTime] = useState(getClosestTimeFromNow());

    const getTimeValues = () => {
        const hours = [];

        for(let hour = 9; hour < 17; hour++) {
            hours.push(moment({ hour }).format('HH:mm'));
            hours.push(
                moment({
                    hour,
                    minute: 30
                }).format('HH:mm')
            );
        }
        hours.pop();
        return hours;
    };

    const handleChange = (event) => {
      setTime(event.target.value);
    };
    return(
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
            <Stack direction='column' className='form-wrapper'>
                <Typography variant="h6">
                Schedule new appointment
                </Typography>

            <FormControl fullWidth>
               <BasicDatePicker />
            </FormControl>
            <FormControl>
            <FormControl sx={{marginTop: '15px', width:'50%'}}>
                <InputLabel id="demo-simple-select-label">Time</InputLabel>
                <Select
                    id="demo-simple-select-label"
                    label="Time"
                    onChange={handleChange}
                    MenuProps={MenuProps}
                    placeholder={getClosestTimeFromNow()}
                    value={time}
                >
                    { getTimeValues().map((time, index) =>
                            <MenuItem value={time} key={index}>{time}</MenuItem>
                     ) }
                </Select>
            </FormControl>
            <TextField 
                id="outlined-search" 
                placeholder="Candidate Name" 
                type="search" 
                sx={{"& fieldset": { border: 'none' }, marginTop: '10px'}} 
                inputProps={{ style: { fontSize: '11pt', padding: '8px' } }} 
            />
            </FormControl>
            <FormControl>

            <TextField 
                id="outlined-search" 
                placeholder="Expirienced Interviewer Name" 
                type="search" 
                sx={{"& fieldset": { border: 'none' }, marginTop: '10px'}} 
                inputProps={{ style: { fontSize: '11pt', padding: '8px' } }} 
            />
            </FormControl>
            <FormControl>
            <TextField 
                id="outlined-search" 
                placeholder="Inexpirienced Interviewer Name" 
                type="search" 
                sx={{"& fieldset": { border: 'none' }, marginTop: '10px'}} 
                inputProps={{ style: { fontSize: '11pt', padding: '8px' } }} 
            />
            
            </FormControl>
            <Stack direction='row' alignSelf='flex-end'>
            <Button autoFocus onClick={handleClose}>
                Close
            </Button>
            <Button onClick={handleClose} autoFocus>
                Submit
            </Button>
            </Stack>
            </Stack>
        </Popover>
    );
}