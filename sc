import requests
from bs4 import BeautifulSoup

baseurl = 'https://www.hcpcsdata.com/'
headers={
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:100.0) Gecko/20100101 Firefox/100.0'
}
r = requests.get('https://www.hcpcsdata.com/Codes')


soup = BeautifulSoup(r.content,'lxml')
productlist = soup.find_all('tr',class_="clickable-row")
productlinks = []
category=[]
group=[]
for item in productlist:
    category.append(item.find_all('td')[2].text.strip())
    # category.append(long)
    for link in item.find_all('a',href=True):
        productlinks.append( baseurl + link['href'])
        group.append('HCPCS ' + link.text)
# print(category,group)
dictionary={
    'group':group,'category':category
}
print(dictionary)
# testlink ='https://www.hcpcsdata.com/Codes/A'
for link1 in productlinks:
    r = requests.get(link1, headers=headers)

    soup = BeautifulSoup(r.content, 'lxml')
    codelist = soup.find_all('tr', class_='clickable-row')
    codelinks = []
    for item in codelist:
        longdescription=item.find_all('td')[1].text.strip()
        for link in item.find_all('a', href=True):
            codelinks.append(baseurl + link['href'])
            code=link.text
# testlink='https://www.hcpcsdata.com/Codes/A/A0021'
for link in codelinks:
    r = requests.get(link, headers=headers)
    soup = BeautifulSoup(r.content, 'lxml')
    lists = soup.find_all('tr')
    short = []
    shorts = []
    for item in lists:
        sh = item.find_all('td')
        short.append(sh)
    for i in short:
        shorts.append(i[1])
    for s in shorts[0]:
        shortdescription = s.strip()
    # print(shortdescription)