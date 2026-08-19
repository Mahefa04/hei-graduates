CREATE TABLE course (
    id UUID PRIMARY KEY,
    ref VARCHAR(255),
    title VARCHAR(255),
    credits INTEGER NOT NULL
);

CREATE TABLE student_group (
    id UUID PRIMARY KEY,
    ref VARCHAR(255),
    track VARCHAR(255)
);

CREATE TABLE promotion (
    id UUID PRIMARY KEY,
    ref VARCHAR(255),
    start_year INTEGER NOT NULL
);

CREATE TABLE teacher (
    id UUID PRIMARY KEY,
    ref VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255)
);

CREATE TABLE student (
    id UUID PRIMARY KEY,
    ref VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    promotion_id UUID,

    CONSTRAINT fk_student_promotion
        FOREIGN KEY (promotion_id)
        REFERENCES promotion(id)
);

CREATE TABLE course_offering (
    id UUID PRIMARY KEY,
    course_id UUID NOT NULL,
    group_id UUID NOT NULL,
    semester VARCHAR(255),
    academic_year VARCHAR(255),

    CONSTRAINT fk_course_offering_course
        FOREIGN KEY (course_id)
        REFERENCES course(id),

    CONSTRAINT fk_course_offering_group
        FOREIGN KEY (group_id)
        REFERENCES student_group(id)
);

CREATE TABLE exam (
    id UUID PRIMARY KEY,
    title VARCHAR(255),
    exam_date TIMESTAMP WITH TIME ZONE,
    coefficient NUMERIC,
    course_offering_id UUID NOT NULL,

    CONSTRAINT fk_exam_course_offering
        FOREIGN KEY (course_offering_id)
        REFERENCES course_offering(id)
);

CREATE TABLE grade (
    id UUID PRIMARY KEY,
    student_id UUID,
    exam_id UUID,
    value NUMERIC,

    CONSTRAINT fk_grade_student
        FOREIGN KEY (student_id)
        REFERENCES student(id),

    CONSTRAINT fk_grade_exam
        FOREIGN KEY (exam_id)
        REFERENCES exam(id)
);

CREATE TABLE grade_history (
    id UUID PRIMARY KEY,
    grade_id UUID,
    old_value NUMERIC,
    new_value NUMERIC,
    reason VARCHAR(255),
    modified_by VARCHAR(255),
    modified_at TIMESTAMP WITH TIME ZONE,

    CONSTRAINT fk_grade_history_grade
        FOREIGN KEY (grade_id)
        REFERENCES grade(id)
);

CREATE TABLE student_group_history (
    id UUID PRIMARY KEY,
    student_id UUID NOT NULL,
    group_id UUID NOT NULL,
    start_date DATE,
    end_date DATE,

    CONSTRAINT fk_student_group_history_student
        FOREIGN KEY (student_id)
        REFERENCES student(id),

    CONSTRAINT fk_student_group_history_group
        FOREIGN KEY (group_id)
        REFERENCES student_group(id)
);

CREATE TABLE teaching_assignment (
    id UUID PRIMARY KEY,
    course_offering_id UUID NOT NULL,
    teacher_id UUID NOT NULL,

    CONSTRAINT fk_teaching_assignment_course_offering
        FOREIGN KEY (course_offering_id)
        REFERENCES course_offering(id),

    CONSTRAINT fk_teaching_assignment_teacher
        FOREIGN KEY (teacher_id)
        REFERENCES teacher(id)
);

CREATE TABLE app_user (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    student_id UUID UNIQUE,
    teacher_id UUID UNIQUE,

    CONSTRAINT fk_app_user_student
        FOREIGN KEY (student_id)
        REFERENCES student(id),

    CONSTRAINT fk_app_user_teacher
        FOREIGN KEY (teacher_id)
        REFERENCES teacher(id)
);