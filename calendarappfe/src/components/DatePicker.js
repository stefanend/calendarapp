import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import dayjs from 'dayjs';

export default function BasicDatePicker() {
  const getDefaultDate = () => {
    const now = dayjs(new Date());
    if(now.hour() >= 16);
      return now.add(1, 'day')
    return now;
  }
  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <DemoContainer components={['DatePicker']}>
        <DatePicker defaultValue={getDefaultDate()} label="Date" />
      </DemoContainer>
    </LocalizationProvider>
  );
}