package ru.itmo.tpl.util;

import ru.itmo.tpl.model.Colors;
import ru.itmo.tpl.model.Post;
import ru.itmo.tpl.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirayanov", "Mikhail Mirzayanov", Colors.RED),
            new User(2, "tourist", "Genady Korotkevich", Colors.GREEN),
            new User(3, "emusk", "Elon Musk", Colors.BLUE),
            new User(5, "pashka", "Pavel Mavrin", Colors.RED),
            new User(7, "geranazavr555", "Georgiy Nazarov", Colors.BLUE),
            new User(11, "cannon147", "Erofey Bashunov", Colors.RED)
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, "Lorem ipsum dolor sit amet", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc, quis gravida magna mi a libero. Fusce vulputate eleifend sapien. Vestibulum purus quam, scelerisque ut, mollis sed, nonummy id, metus. Nullam accumsan lorem in dui. Cras ultricies mi eu turpis hendrerit fringilla. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; In ac dui quis mi consectetuer lacinia. Nam pretium turpis et arcu. Duis arcu tortor, suscipit eget, imperdiet nec, imperdiet iaculis, ipsum. Sed aliquam ultrices mauris. Integer ante arcu, accumsan a, consectetuer eget, posuere ut, mauris. Praesent adipiscing. Phasellus ullamcorper ipsum rutrum nunc. Nunc nonummy metus. Vestibulum volutpat pretium libero. Cras id dui.", 1),
            new Post(2, "Codeforces Round #594 (по задачам МКОШП) [Рейтинговый]", "Всем привет! В воскресенье в Москве пройдет XVII Московская командная олимпиада — командное соревнование для школьников, проходящее в Москве как отборочное соревнование на ВКОШП. Над туром работала Московская методическая комиссия, известная вам также по Открытой олимпиаде школьников по программированию, Московской олимпиаде для 6-9 классов и олимпиаде Мегаполисов", 11),
            new Post(3333, "Вторая командная интернет-олимпиада, Сезон 2019-20", "Всем привет! 19 октября в 15:00 состоится вторая командная интернет-олимпиада для школьников. Приглашаем вас принять в ней участие! В этот раз вам предстоит помочь или помешать Джокеру. Продолжительность олимпиады — 3 часа в базовой и 5 часов в усложненной номинациях. Вы сами можете выбрать номинацию, в которой будете участвовать. Не забудьте зарегистрироваться на цикл командных интернет-олимпиад в этом сезоне перед началом олимпиады, если не сделали этого ранее. Обратите внимание, что для участия в командных олимпиадах, нужно зарегистрировать команду (по ссылке \"Новая команда\"). Команда может содержать от 1 до 3 человек. Дополнительную информацию, а также расписание всех предстоящих командных интернет-олимпиад можно посмотреть на страничке интернет-олимпиад.", 11)
    );

    private static List<User> getUsers() {
        return USERS;
    }

    private static List<Post> getPosts() {
        return POSTS;
    }

    public static void putData(Map<String, Object> data) {
        data.put("users", getUsers());

        for (User user : getUsers()) {
            if (data.get("logged_user_id") != null) {
                long value = (Long)data.get("logged_user_id");
                if (value == user.getId()) {
                    data.put("user", user);
                }
            }
        }

        data.put("posts", getPosts());
    }
}
