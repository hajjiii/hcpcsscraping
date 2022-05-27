import requests
from bs4 import BeautifulSoup
import pandas as pd

baseurl = 'https://www.hcpcsdata.com/'
headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:100.0) Gecko/20100101 Firefox/100.0'
}

r = requests.get('https://www.hcpcsdata.com/Codes')
soup = BeautifulSoup(r.content,'lxml')
results = soup.find_all('tr',class_="clickable-row")
records =[]
for result in results:
    category1 = result.find_all('td')[2].text.strip()
    links = baseurl + result.find('a')['href']
    link = links
    group1 ='HCPCS '+ result.find('a').text
    records.append((category1,link,group1))
# print(records)
# testlink ='https://www.hcpcsdata.com/Codes/A'

for record in records:
    # print(record[1])
    r = requests.get(record[1], headers=headers)
    soup = BeautifulSoup(r.content, 'lxml')
    data = soup.find_all('tr', class_="clickable-row")
    data_records = []
    for item in data:
        longdesc = item.find_all('td')[1].text.strip()
        codelink = item.find('a')['href']
        code = item.find('a').text
        group = record[2]
        category = record[0]
        # print(category)
        data_records.append((group,category,code,longdesc))
        df = pd.DataFrame(data_records, columns=['group','category','code','longdesc'])
        df.head()
        to_csv = df.to_csv(r'C:\Users\Haji\OneDrive\Desktop\webscrap6.csv')


