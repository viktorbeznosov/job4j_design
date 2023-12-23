select person.name, company.name
from person
join company on company.id = person.company_id
where company.id != 5;

select count(person.id) as persons_count, company.name
from company
join person on person.company_id = company.id
group by company.id
order by persons_count desc
limit 1;
