import { FC, ReactNode } from "react";
import styles from "./JobsWrapper.module.scss";

interface JobsWrapperProps {
  children: ReactNode;
}

const JobsWrapper: FC<JobsWrapperProps> = ({ children }) => {
  return (
    <div className={styles.JobsWrapper}>
      {children}
    </div>
  )
}

export default JobsWrapper;