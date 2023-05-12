import { DateCalendar, LocalizationProvider, PickersDay } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import dayjs from "dayjs";
import isBeteenPlugin from "dayjs/plugin/isBetween";
import { styled } from '@mui/material/styles';
import { useState } from "react";
import { Popover } from "@mui/material";


dayjs.extend(isBeteenPlugin);

const CustomPickersDay = styled(PickersDay, {
  shouldForwardProp: (prop) =>
    prop !== 'dayIsBetween' && prop !== 'isFirstDay' && prop !== 'isLastDay',
})(({ theme, dayIsBetween, isFirstDay, isLastDay }) => ({
  ...(dayIsBetween && {
    borderRadius: 0,
    backgroundColor: theme.palette.primary.main,
    color: theme.palette.common.white,
    '&:hover, &:focus': {
      backgroundColor: theme.palette.primary.dark,
    },
  }),
  ...(isFirstDay && {
    borderTopLeftRadius: '50%',
    borderBottomLeftRadius: '50%',
  }),
  ...(isLastDay && {
    borderTopRightRadius: '50%',
    borderBottomRightRadius: '50%',
  }),
}));

function Day(props) {
    const { day, selectedDay, ...other } = props;
  
    if (selectedDay == null) {
      return <PickersDay day={day} {...other} />;
    }
  
    const start = selectedDay.startOf('week');
    const end = selectedDay.endOf('week');
  
    const dayIsBetween = day.isBetween(start, end, null, '[]');
    const isFirstDay = day.isSame(start, 'day');
    const isLastDay = day.isSame(end, 'day');
  
    return (
      <CustomPickersDay
        {...other}
        day={day}
        sx={dayIsBetween ? { px: 2.5, mx: 0 } : {}}
        dayIsBetween={dayIsBetween}
        isFirstDay={isFirstDay}
        isLastDay={isLastDay}
      />
    );
  }

export default function Calendar({ id, anchorEl, open, getAppointmentsForWeek, handleClose }) {
    const [value, setValue] = useState(dayjs(Date.now()));
    
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
        >
            <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DateCalendar 
                    value={value}
                    onChange={(newValue) => { setValue(newValue); getAppointmentsForWeek(newValue)}}
                    slots={{ day: Day }}
                    slotProps={{
                        day: {
                        selectedDay: value,
                        },
                    }}
                    
                />
            </LocalizationProvider>
        </Popover>
    )
}