import { fromUnixTime, formatDistance } from 'date-fns';
import * as R from 'ramda';

const curriedFormatDistance = R.curry(formatDistance);
const distanceFromToday = curriedFormatDistance(new Date());
const addSuffix = (s: unknown): string => `${s} ago`;

export const fromUnixToTodayDistance = R.pipe(
  fromUnixTime,
  distanceFromToday,
  addSuffix
);
