# Java UpSchool Final Project

## Proje Gereklilikleri

- Genel bir restaurant işletmesini simule eden proje.
- Müşteriler bulunacak ve bu müşteriler en çok 2 yemek ve 1 içecek sipariş edebilecek.
- Garsonlar bulunacak ve bu garsonlar müşteriler ile etkileşimde olacak.
- Şefler bulunacak ve şefler yemekleri hazırlayacak.
- Müşteri sayısı genel bağlamda garsonlardan çok, garson sayısı da şeflerden çok olmalıdır.
- Bekleyen müşteriler olabildiği gibi beklemeden giden müşteriler de olabilmekte.
- Bekleyen müşteriler ve siparişler için kuyruk yapısı kullanılacak.
- Şef, müşteri ve garsonlar birer thread olarak çalışacaktır.
- Main classında çalışacak olan kod, singleton olarak tek nesne kullanan bir tasarım çizgisi içerisindedir.

## UML Class Design

- Yukarıdaki maddeler ışığında yapılan uml class diagramı…

![Restaurant drawio](https://user-images.githubusercontent.com/82844813/195339545-73618c63-70b1-48ef-b9d1-b7a8be0f3cc2.png)

## Flow Chart

- Sınıf tasarımının ardından mantık tasarımı olarak gelişen flow chart…

![flow chart](https://user-images.githubusercontent.com/82844813/195339573-01c90347-c69a-4857-9dd1-79eb0f8c9bdb.png)

## Thread Kodları

- Flow charta göre kodlar ile tanıtımlar…
    1. Yeni müşterilerin gelişini de sağlayan bir thread oluşturuldu ve bu thread [Main.java](http://Main.java) içerisinde çalışmakta.
        
        ```java
        public class Main {
        
            public static void main(String[] args) {
        
                // The thread takes new customers to the system.
        
                Thread newCustomerThread = new Thread(new Runnable() {
        
                    @Override
                    public void run() {
                        Main main = Main.getInstance();
                        // the list of customers
                        ArrayList<Customer> list = Customer.getList();
                        list.sort((o1, o2) -> o1.getOrder().getTotalTime(true)- o2.getOrder().getTotalTime(true));
                        Random rand = new Random();
                        // ın every 5 second 5 new customers come to the system.
                        for (int i = 0; i < 3; i++) {
                            System.out.println("%%% NEW CUSTOMERS ARRIVED");
                            for (int j = 0; j < 5; j++) {
                                // some delays.
                                try {
                                    Thread.sleep(rand.nextInt(1500));
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Customer customer = list.get(i * 5 + j);
                                System.out
                                        .println("+++ " + customer.getName() + " NAMED CUSTOMER HAS ARRIVED TO THE RESTAURANT");
            
                            }
        
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
        
                                e.printStackTrace();
                            }
        
                        }
                    }
                });
                newCustomerThread.start();
        
            }
        
        }
        ```
        
    2. Boş masa varsa müşteri masaya yerleştirilir. Eğer boşta masa yoksa müşteri rastgele şekilde beklemek isteyip istemediğini seçer ve bu şekilde de “*waitingCustomer*” kuyruğu dolar. Masaya müşteri yerleştirildiğinde ise “waiter” thread’i çalışır
        
        ```java
        //Main.java içi public static main methodu içerisinde...
        for (int i = 0; i < 3; i++) {
                            System.out.println("%%% NEW CUSTOMERS ARRIVED");
                            for (int j = 0; j < 5; j++) {
                                // some delays.
                                try {
                                    Thread.sleep(rand.nextInt(1500));
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Customer customer = list.get(i * 5 + j);
                                System.out
                                        .println("+++ " + customer.getName() + " NAMED CUSTOMER HAS ARRIVED TO THE RESTAURANT");
                                // emptyIndex holds the value of the empty table ındex.
                                int emptyIndex = -1;
                                boolean isEmptyPlaceExist = false;
                                // looping the tables.
                                for (int k = 0; k < main.tableList.size(); k++) {
                                    // if an empty table exist.
                                    if (main.tableList.get(k).isEmpty()) {
                                        isEmptyPlaceExist = true;
                                        emptyIndex = k;
                                        break;
                                    }
                                }
                                if (isEmptyPlaceExist) {
                                    customer.setTableNumber(emptyIndex);
                                    // sitting the customer.
                                    main.tableList.put(emptyIndex, new Table(customer));
        
                                    // WAITER THREAD WILL WORK
        
                                    main.waiters.work();
        
                                } else {
                                    // The customers who don't want to wait may be exist so we have a boolean
                                    // variable named isWaiting.
                                    boolean isWaiting = rand.nextBoolean();
                                    if (isWaiting) {
                                        main.waitingCustomers.add(customer);
                                        System.out.println("### " + customer.getName() + " NAMED CUSTOMER IS WAITING FOR AN EMPTY TABLE");
        
                                    } else {
                                        System.out.println(
                                                "--- " + customer.getName()
                                                        + " NAMED CUSTOMER DIDN'T WANT TO WAIT AND HAS LEFT FROM THE RESTAURANT");
        
                                    }
        
                                }
                            }
        
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
        
                                e.printStackTrace();
                            }
        
                        }
        ```
        
    3. “waiter” thread’i eğer sipariş vermemiş ve dolu olan bir masa varsa sipariş oluşturulması adına “customer” thread’ini çalıştırır. Bu thread içerisinde iki farklı çalışma şekli vardır. Eğer siparişi şef hazırladıysa customer threadi yemek tüketilme süresi kadar bekleyip masadan kalkar ve eğer “waitingCustomers” kuyruğunda bekleyen müşteri varsa onun masay geçiş işlemini tetikler. Fakat şu anki durumda olduğu gibi eğer henüz siparişi hazır değilse siparişini “orderList” isimli kuyruğa ekler ve sonrasında “chef” thread’i çalışır.
        
        ```java
        //Customer.java sınıfı içerisindeki "run" methodu
        @Override
            public void run() {
                // the main obj
                Main main = Main.getInstance();
                if (isFinished == false) {
        
                    // checking if the order created
                    if (isOrdered == false) {
        
                        // adding the order
                        main.orderList.add(order);
                        isOrdered = true;
                        System.out.println("*** " + getName() + " NAMED CUSTOMER GIVING ORDER");
                    }
        
                } else {
                    // Customer received the order and started to consume.
                    System.out.println("@@@ " + getName() + " NAMED CUSTOMER EATING "
                            + (getGender() == Gender.female ? "HER " : " HIS ") + "ORDER");
                    try {
                        Thread.sleep(order.getTotalTime(false));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // Removing the customer from the table.
                    int key = order.getCustomer().getTableNumber();
                    Customer customer = null;
        
                    // Polling a customer from the queue.
                    try {
                        customer = main.waitingCustomers.poll();
                        customer.setTableNumber(key);
                    } catch (Exception e) {
                    }
        
                    // updating the customer in the table which key is equal to the last order's
                    // key.
        
                    main.tableList.get(key).setCustomer(customer);
                    System.out.println("--- " + order.getCustomer().getName()
                            + " NAMED CUSTOMER FINISHED AND EXITTING THE SYSTEM (CONSUME TIME: " + order.getTotalTime(false) + "  millisecond)" );
        
                    if (customer != null)
                        main.waiters.work();
        
                }
        
            }
        ```
        
    4. “chef” thread’i çalışırken yemeğin oluşturulma süresi kadar bekler ve sonrasında “customer”ın siparişi tüketmesi adına tekrardan “customer” thread’ini çalıştırır. Bu işlemden önce “isFinished” isimli nesne değişkenini de true yapar. Bu şekilde işlemler başa dönerek tamamlanır.
        
        ```java
        //Chef.java içerisindeki run methodu
        @Override
            public void run() {
        
                Main main = Main.getInstance();
        
                // ! Poll = Alır ve kuyruktan çıkartır.
        
                Order order = main.orderList.poll();
        
                if (order.isStarted() == false) {
                    order.setStarted((true));
                    System.out.println("!!! " + getName() + " NAMED CHEF STARTED TO WORK ON ORDER FROM CUSTOMER NAMED "
                            + order.getCustomer().getName() + " (ESTIMATED COOKING TIME:" + order.getTotalTime(true)
                            + " millisecond) : " + order);
        
                    // The thread will sleep until the order is ready.
                    try {
                        Thread.sleep(order.getTotalTime(true));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
        
                    order.getCustomer().setFinished(true);
                    order.getCustomer().run();
        
                }
        
            }
        ```
        

## Log’ların Ekran Görüntüleri
<img width="768" alt="Screen Shot 2022-10-11 at 15 17 28" src="https://user-images.githubusercontent.com/82844813/195339612-62475fe4-aeb9-4d29-b8f2-2b5026274189.png">

