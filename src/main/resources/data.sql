-- =========================
-- USERS
-- =========================
INSERT INTO user_table (user_id, username, email, user_password, created_at)
VALUES
(1, 'alice', 'alice@example.com', '$2a$10$hashedpasswordalice', NOW()),
(2, 'bob', 'bob@example.com', '$2a$10$hashedpasswordbob', NOW());

-- =========================
-- LABELS
-- =========================
INSERT INTO label (label_id, label_name, user_id)
VALUES
(1, 'Work', 1),
(2, 'Personal', 1),
(3, 'Ideas', 2),
(4, 'Travel', 2);

-- =========================
-- COLORS
-- =========================
INSERT INTO notecolor (color_id, user_id, color_hex)
VALUES
(1, 1, '#FF5733'),
(2, 1, '#33C1FF'),
(3, 2, '#FF33A8'),
(4, 2, '#33FF57');

-- =========================
-- NOTES
-- =========================
INSERT INTO note (
  note_id, user_id, title, text_content,
  label_id, color_id,
  pinned, hidden,
  cosmetics, view_only,
  created_at, updated_at,
  deleted, time_left_before_deletion
)
VALUES
-- Alice notes
(1, 1, 'Sprint Planning', 'Finish backend API and Docker setup', 1, 1, true, false, NULL, false, NOW(), NOW(), false, NULL),
(2, 1, 'Grocery List', 'Milk, eggs, bread', 2, 2, false, false, NULL, false, NOW(), NOW(), false, NULL),

-- Bob notes
(3, 2, 'Startup Idea', 'AI-powered habit tracker', 3, 3, true, false, NULL, false, NOW(), NOW(), false, NULL),
(4, 2, 'Japan Trip', 'Visit Tokyo, Osaka, Kyoto', 4, 4, false, false, NULL, false, NOW(), NOW(), false, NULL);

-- =========================
-- UI TEMPLATES
-- =========================
INSERT INTO uitemplate (template_id, user_id, template_name, template_details)
VALUES
(1, 1, 'Default Layout', '{"theme":"light","layout":"grid"}'),
(2, 1, 'Focus Mode', '{"theme":"dark","layout":"single-column"}'),
(3, 2, 'Travel Mode', '{"theme":"light","layout":"map"}'),
(4, 2, 'Idea Board', '{"theme":"dark","layout":"kanban"}');