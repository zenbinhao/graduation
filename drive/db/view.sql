


create view view_news_payment(user_id,user_name,pay_id, pay_way, pay_content) as
select drive_user.id,drive_user.user_name,drive_payment.id,drive_payment.pay_way, drive_payment.content
from drive_payment inner join drive_user on drive_payment.fk_user_id = drive_user.id
where drive_payment.is_check = 0 and drive_payment.is_deleted = 0


create view view_news_delExam(user_id,user_name,exam_id, exam_subject) as
select drive_user.id,drive_user.user_name,drive_examsubscribe.id,drive_examsubscribe.`subject`
from drive_examsubscribe inner join drive_user on drive_examsubscribe.fk_user_id = drive_user.id
where drive_examsubscribe.is_response = 0 and drive_examsubscribe.is_deleted = 0


create view view_news_passExam(user_id, user_name, exam_id, exam_subject) as
select drive_user.id,drive_user.user_name,drive_examsubscribe.id,drive_examsubscribe.`subject`
from drive_examsubscribe inner join drive_user on drive_examsubscribe.fk_user_id = drive_user.id
where drive_examsubscribe.is_response = 1 and drive_examsubscribe.is_pass = 0 and drive_examsubscribe.is_deleted = 0
